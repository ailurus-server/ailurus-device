package ca.ailurus.dashboard.objects.device;

/**
 * Created by Me on 2014-08-14.
 */
public class Memory {
    public static class Usage {
        public long used;
        public long free;
        public long total;
    }

    public Usage system = new Usage();
    public Usage jvm = new Usage();
    public long speed;
}
