package ca.ailurus.dashboard.api;

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

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

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

            DeviceSettings settings = new DeviceSettings();
            settings.url = init.url;
            tx.createSettings(settings);

            tx.commit();
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
        cpu.type = "ARM1176JZF-S";
        cpu.architecture = "32-bit";
        cpu.speed = 700000000;
        cpu.numCores = Runtime.getRuntime().availableProcessors();
        return cpu;
    }

    @GET @Path("/memory")
    public Memory getMemory() {
        Memory memory = new Memory();

        HardwareAbstractionLayer hal = new SystemInfo().getHardware();
        memory.system.free = hal.getMemory().getAvailable();
        memory.system.total = hal.getMemory().getTotal();
        memory.system.used = memory.system.total - memory.system.free;

        Runtime runtime = Runtime.getRuntime();
        memory.jvm.free = runtime.freeMemory();
        memory.jvm.total = runtime.totalMemory();
        memory.jvm.used = memory.jvm.total - memory.jvm.free;

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
            network.ethernet = "10/100 Mbit/s";

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
