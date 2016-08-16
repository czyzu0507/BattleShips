package io.github.expansionteam.battleships.logic;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConnectionConfigTest {

    private ConnectionConfig connectionConfig;
    private String LOCALHOST = "127.0.0.1";


    @BeforeMethod
    public void setUp() {
        connectionConfig = new ConnectionConfig();
    }

    @Test
    public void testGetServerIp() throws Exception {
        assertThat(connectionConfig.getServerIp(), is(LOCALHOST));
    }

    @Test
    public void testSetServerIp() throws Exception {
        String serverIp = "10.20.30.10";
        connectionConfig.setServerIp(serverIp);
        assertThat(connectionConfig.getServerIp(), is(serverIp));
        assertThat(connectionConfig.getServerIp(), is(not(LOCALHOST)));
    }

}