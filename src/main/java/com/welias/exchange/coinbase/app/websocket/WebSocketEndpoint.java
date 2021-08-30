package com.welias.exchange.coinbase.app.websocket;

import com.welias.exchange.coinbase.app.message.MessageDecoder;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class WebSocketEndpoint
{
    private final MessageHandler mMessageHandler;
    private final URI mURI;

    private Session mSession;

    public WebSocketEndpoint(
            URI endpointURI,
            MessageHandler messageHandler)
    {
        mURI = endpointURI;
        mMessageHandler = messageHandler;
    }

    public void connect() throws DeploymentException, IOException
    {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        mSession = container.connectToServer(this, mURI);
    }

    @OnOpen
    public void onOpen(Session session)
    {
        mSession = session;
    }

    @OnMessage
    public void onMessage(Session session, String message)
    {
        mMessageHandler.handleMessage(MessageDecoder.decodeMessage(message));
    }

    @OnClose
    public void onClose(Session session)
    {
        mSession = null;
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        System.out.printf("Received ws error for session %s %s %n", session, throwable.getMessage());
    }

    public void sendMessage(String message)
    {
        mSession.getAsyncRemote().sendText(message);
    }

    public void close()
    {
        if (mSession != null && mSession.isOpen())
        {
            try
            {
                mSession.close();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
