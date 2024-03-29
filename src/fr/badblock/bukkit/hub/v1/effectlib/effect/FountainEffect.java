package fr.badblock.bukkit.hub.v1.effectlib.effect;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v1.effectlib.Effect;
import fr.badblock.bukkit.hub.v1.effectlib.EffectManager;
import fr.badblock.bukkit.hub.v1.effectlib.EffectType;
import fr.badblock.bukkit.hub.v1.effectlib.util.ParticleEffect;
import fr.badblock.bukkit.hub.v1.effectlib.util.RandomUtils;

public class FountainEffect extends Effect {

	/**
	 * Height of the fountain
	 */
	public float height = 3;

	/**
	 * Height of the spout in blocks
	 */
	public float heightSpout = 7;

	/**
	 * Particle of the fountain
	 */
	public ParticleEffect particle = ParticleEffect.WATER_SPLASH;

	/**
	 * Particles per iteration in the spout
	 */
	public int particlesSpout = 200;

	/**
	 * Particles per iteration per strand (100)
	 */
	public int particlesStrand = 150;

	/**
	 * Radius of strands in blocks
	 */
	public float radius = 5;

	/**
	 * Radius of spout as a fraction of the fountain (0.1)
	 */
	public float radiusSpout = .1f;

	/**
	 * Rotation of the fountain on the Y-Axis (Fraction of PI)
	 */
	public double rotation = Math.PI / 4;

	/**
	 * Amount of strands (10)
	 */
	public int strands = 10;

	public FountainEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 2;
		iterations = 100;
	}

	@Override
	public void onRun() {
		Location location = getLocation();
		for (int i = 1; i <= strands; i++) {
			double angle = 2 * i * Math.PI / strands + rotation;
			for (int j = 1; j <= particlesStrand; j++) {
				float ratio = (float) j / particlesStrand;
				double x, y, z;
				x = Math.cos(angle) * radius * ratio;
				y = Math.sin(Math.PI * j / particlesStrand) * height;
				z = Math.sin(angle) * radius * ratio;
				location.add(x, y, z);
				display(particle, location);
				location.subtract(x, y, z);
			}
		}
		for (int i = 0; i < particlesSpout; i++) {
			Vector v = RandomUtils.getRandomCircleVector()
					.multiply(RandomUtils.random.nextFloat() * radius * radiusSpout);
			v.setY(RandomUtils.random.nextFloat() * heightSpout);
			location.add(v);
			display(particle, location);
			location.subtract(v);
		}
	}

}
