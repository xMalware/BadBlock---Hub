package fr.badblock.bukkit.hub.v1.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v1.effectlib.Effect;
import fr.badblock.bukkit.hub.v1.effectlib.EffectManager;
import fr.badblock.bukkit.hub.v1.effectlib.EffectType;
import fr.badblock.bukkit.hub.v1.effectlib.util.MathUtils;
import fr.badblock.bukkit.hub.v1.effectlib.util.ParticleEffect;
import fr.badblock.bukkit.hub.v1.effectlib.util.VectorUtils;

/**
 * Creates an animated Sphere.. Thanks to the author for sharing it!
 * https://www.youtube.com/watch?feature=player_embedded&v=RUjIw_RprRw
 *
 * @author <a href="http://forums.bukkit.org/members/qukie.90952701/">Qukie</a>
 */
public class AnimatedBallEffect extends Effect {

	/**
	 * ParticleType of spawned particle
	 */
	public ParticleEffect particle = ParticleEffect.SPELL_WITCH;

	/**
	 * Ball particles total (150)
	 */
	public int particles = 150;

	/**
	 * The amount of particles, displayed in one iteration (10)
	 */
	public int particlesPerIteration = 10;

	/**
	 * Size of this ball (1)
	 */
	public float size = 1F;

	/**
	 * Internal Counter
	 */
	protected int step;

	/**
	 * Factors (1, 2, 1)
	 */
	public float xFactor = 1F, yFactor = 2F, zFactor = 1F;

	/**
	 * Offsets (0, 0.8, 0)
	 */
	public float xOffset, yOffset = 0.8F, zOffset;

	/**
	 * Rotation of the ball.
	 */
	public double xRotation, yRotation, zRotation = 0;

	public AnimatedBallEffect(EffectManager effectManager) {
		super(effectManager);
		this.type = EffectType.REPEATING;
		this.iterations = 500;
		this.period = 1;
	}

	@Override
	public void onRun() {
		Vector vector = new Vector();
		Location location = getLocation();
		for (int i = 0; i < particlesPerIteration; i++) {
			step++;

			float t = (MathUtils.PI / particles) * step;
			float r = MathUtils.sin(t) * size;
			float s = 2 * MathUtils.PI * t;

			vector.setX(xFactor * r * MathUtils.cos(s) + xOffset);
			vector.setZ(zFactor * r * MathUtils.sin(s) + zOffset);
			vector.setY(yFactor * size * MathUtils.cos(t) + yOffset);

			VectorUtils.rotateVector(vector, xRotation, yRotation, zRotation);

			display(particle, location.add(vector));
			location.subtract(vector);
		}
	}

}
