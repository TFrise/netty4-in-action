package com.mindflow.netty4.rpc.core;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelWrapper {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ChannelFuture channelFuture;

    public ChannelWrapper(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public boolean isActive() {
        return (this.channelFuture.channel() != null && this.channelFuture.channel().isActive());
    }

    public boolean isWritable() {
        return this.channelFuture.channel().isWritable();
    }


    public Channel getChannel() {
        return this.channelFuture.channel();
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void close(){
        getChannel().close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("closeChannel: close the connection to remote address:{}, result: {}",
                        getChannel().remoteAddress(), future.isSuccess());
            }
        });
    }
}