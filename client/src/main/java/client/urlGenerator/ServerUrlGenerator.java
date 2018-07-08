package client.urlGenerator;

import client.ServerProperties;

public abstract class ServerUrlGenerator
{
    protected static final String HTTP = "http://";
    protected ServerProperties serverProperties;

    public ServerUrlGenerator(ServerProperties serverProperties)
    {
        this.serverProperties = serverProperties;
    }
}
