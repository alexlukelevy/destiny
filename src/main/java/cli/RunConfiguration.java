package cli;

public class RunConfiguration {

    public String gamerTag;
    public String psnId;
    public String psnPass;

    public RunConfiguration(String gamerTag, String psnId, String psnPass) {
        this.gamerTag = gamerTag;
        this.psnId = psnId;
        this.psnPass = psnPass;
    }
}