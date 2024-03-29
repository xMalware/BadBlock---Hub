package fr.badblock.bukkit.hub.v1.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v1.effectlib.Effect;
import fr.badblock.bukkit.hub.v1.effectlib.EffectManager;
import fr.badblock.bukkit.hub.v1.effectlib.util.MathUtils;
import fr.badblock.bukkit.hub.v1.effectlib.util.ParticleEffect;
import fr.badblock.bukkit.hub.v1.effectlib.util.VectorUtils;

/**
 * Creates a 2D Heart in 3D space. Thanks to the author for sharing it!
 *
 * @author <a href="http://forums.bukkit.org/members/qukie.90952701/">Qukie</a>
 */
public class HeartEffect extends Effect {

	/**
	 * Compilation of the heart. (2)
	 */
	public float compilaction = 2F;

	/**
	 * Compresses the heart along the y axis. (2)
	 */
	public double compressYFactorTotal = 2;

	/**
	 * Defines the size of the that inner sting (0.8) \/
	 */
	public double factorInnerSpike = 0.8;

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.CRIT_MAGIC;

	/**
	 * Heart-particles per interation (100)
	 */
	public int particles = 50;

	/**
	 * Rotation of the heart.
	 */
	public double xRotation, yRotation, zRotation = 0;

	/**
	 * Strech/Compress factor along the x or y axis (1, 1)
	 */
	public double yFactor = 1, xFactor = 1;

	public HeartEffect(EffectManager effectManager) {
		super(effectManager);
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		Vector vector = new Vector();
		for (int i = 0; i < particles; i++) {
			float alpha = ((MathUtils.PI / compilaction) / particles) * i;
			double phi = Math.pow(
					Math.abs(MathUtils.sin(2 * compilaction * alpha))
							+ factorInnerSpike * Math.abs(MathUtils.sin(compilaction * alpha)),
					1 / compressYFactorTotal);

			vector.setY(phi * (MathUtils.sin(alpha) + MathUtils.cos(alpha)) * yFactor);
			vector.setZ(phi * (MathUtils.cos(alpha) - MathUtils.sin(alpha)) * xFactor);

			VectorUtils.rotateVector(vector, xRotation, yRotation, zRotation);

			display(particle, location.add(vector));
			location.subtract(vector);
		}
	}

}
