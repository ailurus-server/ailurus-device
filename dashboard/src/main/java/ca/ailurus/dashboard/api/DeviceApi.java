package ca.ailurus.dashboard.api;

import ca.ailurus.dashboard.api.objects.Device;
import ca.ailurus.dashboard.api.objects.device.Cpu;
import ca.ailurus.dashboard.api.objects.device.Disk;
import ca.ailurus.dashboard.api.objects.device.Memory;
import ca.ailurus.dashboard.api.objects.device.Network;
import ca.ailurus.entities.DeviceSettings;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.sql.SQLException;

@Path("/device")
public class DeviceApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice() {
        Device device = new Device();
        device.cpu = getCpu();
        device.memory = getMemory();
        device.disk = getDisk();
        device.network = getNetwork();

        return device;
    }

    @GET @Path("/cpu")
    @Produces(MediaType.APPLICATION_JSON)
    public Cpu getCpu() {
        Cpu cpu = new Cpu();

//        in.getMhz();
//        in.getModel();
        cpu.type = "ARM Cortex-A7";
        cpu.architecture = "32-bit";
        cpu.numCores = Runtime.getRuntime().availableProcessors();
        return cpu;
    }

    @GET @Path("/memory")
    @Produces(MediaType.APPLICATION_JSON)
    public Memory getMemory() {
        Memory memory = new Memory();

        memory.free = Runtime.getRuntime().freeMemory();
        memory.total = Runtime.getRuntime().totalMemory();
        memory.used = memory.total - memory.free;
        memory.speed = 960000000;

        return memory;
    }

    @GET @Path("/disk")
    @Produces(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
    public Network getNetwork() {
        Network network = new Network();

        DeviceSettings settings;
        try {
            settings = DeviceSettings.getSettings();
        } catch(SQLException e) {
            throw new InternalServerErrorException(e);
        }

        network.hostname = "apple";
        network.ipAddress = "99.235.253.170";
        network.url = settings.url;
        network.capacity = "10/100 ethernet";

        return network;
    }
}