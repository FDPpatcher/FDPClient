/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other

import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.PacketUtils
import net.ccbluex.liquidbounce.value.FloatValue
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
import kotlin.math.cos
import kotlin.math.sin

class GroundPacket : SpeedMode("GroundPacket") {
    
    private val moveSpeed = FloatValue("${valuePrefix}Speed", 0.6f, 0.27f, 5f)
    private val baseSpeed = FloatValue("${valuePrefix}DistPerPacket", 0.15f, 0.12f, 0.2873f)
  
    override fun onUpdate() {
        if (!mc.thePlayer.onGround) return
        var s = moveSpeed.get().toDouble()
        var x = mc.thePlayer.posX
        var z = mc.thePlayer.posZ
        var d = baseSpeed.get().toDouble()
        var yaw = Math.toRadians(mc.thePlayer.rotationYaw.toDouble())
        var mx = -sin(yaw) * d
        var mz = cos(yaw) * d
        while (d < s) {
            if (d > s) {
                d = s
            }
            yaw = Math.toRadians(mc.thePlayer.rotationYaw.toDouble())
            mx = -sin(yaw) * d
            mz = cos(yaw) * d
            PacketUtils.sendPacketNoEvent(C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround))
            mc.thePlayer.setPosition(x + mx, mc.thePlayer.posY, z + mz)
            d += d = baseSpeed.get().toDouble()
        }
    }
}
