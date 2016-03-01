package KhorneSyrup.PolyMath.Common.network;

import KhorneSyrup.PolyMath.PolyMath;
import KhorneSyrup.PolyMath.Common.network.client.SyncPlayerPropsMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketDispatcher {

	// This counter helps to rid us of "magic" numbers used during packet creation
	private static byte packetId = 0;

	// this is used both for registering and sending packets.
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(PolyMath.MOD_ID);


	public static final void registerPackets() {
		//PacketsHandled by the CLIENT go here.

		registerMessage(SyncPlayerPropsMessage.class);

		//Packets Handled by the SERVER go here.

		//registerMessage(OpenGuiMessage.class);
	}



		private static final <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(Class<T> clazz) {
			// We can tell by the message class which side to register it on by using #isAssignableFrom (google it)

			// Also, one can see the convenience of using a static counter 'packetId' to keep
			// track of the current index, rather than hard-coding them all, plus it's one less
			// parameter to pass.
			if (AbstractMessage.AbstractClientMessage.class.isAssignableFrom(clazz)) {
				PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId++, Side.CLIENT);
			} else if (AbstractMessage.AbstractServerMessage.class.isAssignableFrom(clazz)) {
				PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId++, Side.SERVER);
			} else {
				// hopefully you didn't forget to extend the right class, or you will get registered on both sides
				PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId, Side.CLIENT);
				PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId++, Side.SERVER);
			}
		}

		//========================================================//
		// The following methods are the 'wrapper' methods; again,
		// this just makes sending a message slightly more compact
		// and is purely a matter of stylistic preference
		//========================================================//

		/**
		 * Send this message to the specified player's client-side counterpart.
		 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
		 */
		public static final void sendTo(IMessage message, EntityPlayerMP player) {
			PacketDispatcher.dispatcher.sendTo(message, player);
		}

		/**
		 * Send this message to everyone.
		 * See {@link SimpleNetworkWrapper#sendToAll(IMessage)}
		 */
		public static void sendToAll(IMessage message) {
			PacketDispatcher.dispatcher.sendToAll(message);
		}

		/**
		 * Send this message to everyone within a certain range of a point.
		 * See {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
		 */
		public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
			PacketDispatcher.dispatcher.sendToAllAround(message, point);
		}

		/**
		 * Sends a message to everyone within a certain range of the coordinates in the same dimension.
		 * Shortcut to {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
		 */
		public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
			PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
		}

		/**
		 * Sends a message to everyone within a certain range of the player provided.
		 * Shortcut to {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
		 */
		public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
			PacketDispatcher.sendToAllAround(message, player.worldObj.provider.getDimensionId(), player.posX, player.posY, player.posZ, range);
		}

		/**
		 * Send this message to everyone within the supplied dimension.
		 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
		 */
		public static final void sendToDimension(IMessage message, int dimensionId) {
			PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
		}

		/**
		 * Send this message to the server.
		 * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
		 */
		public static final void sendToServer(IMessage message) {
			PacketDispatcher.dispatcher.sendToServer(message);
		}
	}
