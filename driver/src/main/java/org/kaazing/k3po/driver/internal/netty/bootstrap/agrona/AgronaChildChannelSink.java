/*
 * Copyright 2014, Kaazing Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kaazing.k3po.driver.internal.netty.bootstrap.agrona;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.kaazing.k3po.driver.internal.netty.bootstrap.channel.AbstractChannelSink;

public class AgronaChildChannelSink extends AbstractChannelSink {

    @Override
    protected void writeRequested(ChannelPipeline pipeline, MessageEvent evt) throws Exception {
        ChannelBuffer channelBuffer = (ChannelBuffer) evt.getMessage();
        AgronaChildChannel channel = (AgronaChildChannel) evt.getChannel();
        ChannelFuture writeFuture = evt.getFuture();
        channel.worker.write(channel, channelBuffer, writeFuture);
    }

    @Override
    protected void closeRequested(ChannelPipeline pipeline, ChannelStateEvent evt) throws Exception {
        AgronaChildChannel channel = (AgronaChildChannel) evt.getChannel();
        channel.worker.close(channel);
    }

}
