package antitheftproject.android.cmpe277.antitheftproject.model;

public class NetworkPojo {
    private int networkId;
    private boolean isWifiNetwork;
    private String networkName;
    private String networkState;

    public String getNetworkState() {
        return networkState;
    }

    public void setNetworkState(String networkState) {
        this.networkState = networkState;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public boolean isWifiNetwork() {
        return isWifiNetwork;
    }

    public void setWifiNetwork(boolean wifiNetwork) {
        isWifiNetwork = wifiNetwork;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }
}