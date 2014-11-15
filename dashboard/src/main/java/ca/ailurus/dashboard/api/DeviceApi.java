package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.entities.UseCase;
import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import ca.ailurus.dashboard.objects.Device;
import ca.ailurus.dashboard.objects.Initialization;
import ca.ailurus.dashboard.objects.device.Cpu;
import ca.ailurus.dashboard.objects.device.Disk;
import ca.ailurus.dashboard.objects.device.Memory;
import ca.ailurus.dashboard.objects.device.Network;
import ca.ailurus.dashboard.entities.User;
import ca.ailurus.dashboard.entities.DeviceSettings;

import com.google.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Path("/device")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceApi {
    private TransactionMaker transactionMaker;

    @Inject
    public DeviceApi(TransactionMaker transactionMaker) {
        this.transactionMaker = transactionMaker;
    }

    @POST @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    public void init(Initialization init) {
        try (Transaction tx = transactionMaker.make()) {
            if (tx.hasSettings()) {
                throw new BadRequestException("Device has already been initialized.");
            }

            User user = new User();
            user.name = init.username;
            user.password = init.password;
            user.email = init.email;
            tx.addUser(user);

            initUseCases(tx);

            DeviceSettings settings = new DeviceSettings();
            settings.url = init.url;
            tx.createSettings(settings);

            tx.commit();
        }
    }

    private void initUseCases(Transaction tx) {
         UseCase[] useCases = {
            new UseCase("blog", "Blog", " to share your thoughts", UseCase.Types.Personal),
            new UseCase("profile", "Profile Page", " to showcase your work", UseCase.Types.Personal),
            new UseCase("game-server", "Game Server", " to host games for your friends", UseCase.Types.Personal),
            new UseCase("corporate-website", "Corporate Website", " to showcase your company", UseCase.Types.Business),
            new UseCase("web-server", "Web Server", " to run your own website", UseCase.Types.Programming),
            new UseCase("source-control", "Source Control", " to safely store your source code", UseCase.Types.Programming)
        };

        for (UseCase useCase: useCases) {
            tx.addUseCase(useCase);
        }
    }

    @GET
    public Device getDevice() {
        Device device = new Device();
        device.cpu = getCpu();
        device.memory = getMemory();
        device.disk = getDisk();
        device.network = getNetwork();

        return device;
    }

    @GET @Path("/cpu")
    public Cpu getCpu() {
        Cpu cpu = new Cpu();
        cpu.type = "ARM Cortex-A7";
        cpu.architecture = "32-bit";
        cpu.numCores = Runtime.getRuntime().availableProcessors();
        return cpu;
    }

    @GET @Path("/memory")
    public Memory getMemory() {
        Memory memory = new Memory();

        memory.free = Runtime.getRuntime().freeMemory();
        memory.total = Runtime.getRuntime().totalMemory();
        memory.used = memory.total - memory.free;
        memory.speed = 960000000;

        return memory;
    }

    @GET @Path("/disk")
    public Disk getDisk() {
        Disk disk = new Disk();

        for (File root: File.listRoots()) {
            disk.free += root.getFreeSpace();
            disk.total += root.getTotalSpace();
        }

        disk.used = disk.total - disk.free;
        disk.type = "NAND Flash";

        return disk;
    }

    @GET @Path("/network")
    public Network getNetwork() {
        try (Transaction tx = transactionMaker.make()) {
            Network network = new Network();
            InetAddress address = getAddress();
            DeviceSettings settings = tx.getSettings();

            network.hostname = address.getHostName();
            network.ipAddress = address.getHostAddress();
            network.url = settings.url;
            network.capacity = "10/100 ethernet";

            return network;
        }
    }

    private InetAddress getAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
