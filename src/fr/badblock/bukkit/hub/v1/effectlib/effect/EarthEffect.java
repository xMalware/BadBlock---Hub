package fr.badblock.bukkit.hub.v1.effectlib.effect;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.badblock.bukkit.hub.v1.effectlib.Effect;
import fr.badblock.bukkit.hub.v1.effectlib.EffectManager;
import fr.badblock.bukkit.hub.v1.effectlib.EffectType;
import fr.badblock.bukkit.hub.v1.effectlib.util.MathUtils;
import fr.badblock.bukkit.hub.v1.effectlib.util.ParticleEffect;
import fr.badblock.bukkit.hub.v1.effectlib.util.RandomUtils;
import fr.badblock.bukkit.hub.v1.effectlib.util.VectorUtils;

public class EarthEffect extends Effect {

	/**
	 * Caches vectors to increase performance
	 */
	protected final Set<Vector> cacheGreen, cacheBlue;

	/**
	 * Triggers invalidation on first run
	 */
	protected boolean firstStep = true;

	/**
	 * Height of the mountains.
	 */
	public float mountainHeight = .5f;

	/**
	 * Amount of Particles to form the World
	 */
	public int particles = 500;

	/**
	 * Precision of generation. Higher numbers have better results, but increase
	 * the time of generation. Don't pick Number above 10.000
	 */
	public int precision = 100;

	/**
	 * Radius of the World
	 */
	public float radius = 1;

	/**
	 * Current step. Works as counter
	 */
	protected int step = 0;

	public EarthEffect(EffectManager effectManager) {
		super(effectManager);
		type = EffectType.REPEATING;
		period = 5;
		iterations = 200;
		cacheGreen = new HashSet<Vector>();
		cacheBlue = new HashSet<Vector>();
	}

	public void invalidate() {
		firstStep = false;
		cacheGreen.clear();
		cacheBlue.clear();

		Set<Vector> cache = new HashSet<Vector>();
		int sqrtParticles = (int) Math.sqrt(particles);
		float theta = 0, phi, thetaStep = MathUtils.PI / sqrtParticles, phiStep = MathUtils.PI2 / sqrtParticles;
		for (int i = 0; i < sqrtParticles; i++) {
			theta += thetaStep;
			phi = 0;
			for (int j = 0; j < sqrtParticles; j++) {
				phi += phiStep;
				float x = radius * MathUtils.sin(theta) * MathUtils.cos(phi);
				float y = radius * MathUtils.sin(theta) * MathUtils.sin(phi);
				float z = radius * MathUtils.cos(theta);
				cache.add(new Vector(x, y, z));
			}
		}

		float increase = mountainHeight / precision;
		for (int i = 0; i < precision; i++) {
			double r1 = RandomUtils.getRandomAngle(), r2 = RandomUtils.getRandomAngle(),
					r3 = RandomUtils.getRandomAngle();
			for (Vector v : cache) {
				if (v.getY() > 0) {
					v.setY(v.getY() + increase);
				} else {
					v.setY(v.getY() - increase);
				}
				if (i != precision - 1) {
					VectorUtils.rotateVector(v, r1, r2, r3);
				}
			}
		}

		float minSquared = Float.POSITIVE_INFINITY, maxSquared = Float.NEGATIVE_INFINITY;
		for (Vector current : cache) {
			float lengthSquared = (float) current.lengthSquared();
			if (minSquared > lengthSquared) {
				minSquared = lengthSquared;
			}
			if (maxSquared < lengthSquared) {
				maxSquared = lengthSquared;
			}
		}

		// COLOR PARTICLES
		float average = (minSquared + maxSquared) / 2;
		for (Vector v : cache) {
			float lengthSquared = (float) v.lengthSquared();
			if (lengthSquared >= average) {
				cacheGreen.add(v);
			} else {
				cacheBlue.add(v);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRun() {
		Location location = getLocation();
		if (firstStep) {
			invalidate();
		}
		for (Vector v : cacheGreen) {
			ParticleEffect.VILLAGER_HAPPY.display(location.add(v), visibleRange, 0, 0, 0, 0, 3);
			location.subtract(v);
		}
		for (Vector v : cacheBlue) {
			ParticleEffect.DRIP_WATER.display(location.add(v), visibleRange, 0, 0, 0, 0, 1);
			location.subtract(v);
		}
	}
}
