package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.exceptions.AlreadyInitializedException;
import ca.ailurus.dashboard.objects.Device;
import ca.ailurus.dashboard.objects.Initialization;
import ca.ailurus.dashboard.objects.device.Cpu;
import ca.ailurus.dashboard.objects.device.Disk;
import ca.ailurus.dashboard.objects.device.Memory;
import ca.ailurus.dashboard.objects.device.Network;
import ca.ailurus.dashboard.entities.User;
import ca.ailurus.dashboard.entities.DeviceSettings;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

@Path("/device")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceApi {
    @POST @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    public void init(Initialization init) throws SQLException {
        DeviceSettings settings = DeviceSettings.getSettings();

        if (settings.initialized) {
            throw new AlreadyInitializedException();
        }

        User user = new User();
        user.name = init.username;
        user.password = init.password;
        user.email = init.email;
        User.create(user);

        settings.initialized = true;
        settings.url = init.url;
        DeviceSettings.update(settings);
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
        Network network = new Network();

        DeviceSettings settings;
        try {
            settings = DeviceSettings.getSettings();
        } catch(SQLException e) {
            throw new InternalServerErrorException(e);
        }

        InetAddress address = getAddress();

        network.hostname = address.getHostName();
        network.ipAddress = address.getHostAddress();
        network.url = settings.url;
        network.capacity = "10/100 ethernet";

        return network;
    }

    private InetAddress getAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
