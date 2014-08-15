package ca.ailurus.dashboard.api.objects;

import ca.ailurus.dashboard.api.objects.device.Cpu;
import ca.ailurus.dashboard.api.objects.device.Disk;
import ca.ailurus.dashboard.api.objects.device.Memory;
import ca.ailurus.dashboard.api.objects.device.Network;

public class Device {
    public Cpu cpu;
    public Memory memory;
    public Disk disk;
    public Network network;
}
