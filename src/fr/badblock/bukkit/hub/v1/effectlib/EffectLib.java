package fr.badblock.bukkit.hub.v1.effectlib;

import java.util.List;

import org.bukkit.Bukkit;

import fr.badblock.bukkit.hub.v1.BadBlockHub;
import fr.badblock.bukkit.hub.v1.effectlib.entity.EntityManager;
import fr.badblock.bukkit.hub.v1.effectlib.listener.ItemListener;

/*! \mainpage EffectLib Plugin API
*
* \section intro_sec Introduction
*
* This is the API for EffectLib, which gives developers access
* to a wide variety of visual effects for use in their Plugins.
*
* \section issues_sec Issues
*
* For issues with the API, or suggestions, please use the devbukkit
* project page:
*
* http://dev.bukkit.org/bukkit-plugins/effectlib/
*
* \section start_sec Getting Started
*
* If you haven't done so already, get started with Bukkit by getting a basic
* shell of a plugin working. You should at least have a working Plugin that
* loads in Bukkit (add a debug print to onEnable to be sure!) before you
* start trying to integrate with other Plugins. See here for general help:
*
* http://wiki.bukkit.org/Plugin_Tutorial
*
* \section maven_sec Building with Maven
*
* Once you have a project set up, it is easy to build against EffectLib
* with Maven. Simply add the elmakers repository to your repository list,
* and then add a dependency for EffectLib. A typical setup would look like:
*
* <pre>
* &lt;dependencies&gt;
* &lt;dependency&gt;
* 	&lt;groupId&gt;org.bukkit&lt;/groupId&gt;
* 	&lt;artifactId&gt;bukkit&lt;/artifactId&gt;
* 	&lt;version&gt;1.6.4-R2.0&lt;/version&gt;
* 	&lt;scope&gt;provided&lt;/scope&gt;
* &lt;/dependency&gt;
* &lt;dependency&gt;
* 	&lt;groupId&gt;de.slikey&lt;/groupId&gt;
* 	&lt;artifactId&gt;EffectLib&lt;/artifactId&gt;
* 	&lt;version&gt;1.4&lt;/version&gt;
* 	&lt;scope&gt;provided&lt;/scope&gt;
* &lt;/dependency&gt;
* &lt;/dependencies&gt;
* &lt;repositories&gt;
* &lt;repository&gt;
*     &lt;id&gt;bukkit-repo&lt;/id&gt;
*     &lt;url&gt;http://repo.bukkit.org/content/groups/public/ &lt;/url&gt;
* &lt;/repository&gt;
* &lt;repository&gt;
*     &lt;id&gt;elmakers-repo&lt;/id&gt;
*     &lt;url&gt;http://maven.elmakers.com/repository/ &lt;/url&gt;
* &lt;/repository&gt;
* &lt;/repositories&gt;
* </pre>
*
* If you don't want to depend on EffectLib as an external library, you
* can use the Maven shade plugin to "shade" the library into your own plugin.
* This will relocate the library so you don't conflict with another instance
* of it, even though all the EffectLib code is built in. Here is an example pom section:
*
* <pre>
* &lt;plugin&gt;
*     &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
*     &lt;artifactId&gt;maven-shade-plugin&lt;/artifactId&gt;
*     &lt;version&gt;1.5&lt;/version&gt;
*     &lt;executions&gt;
*         &lt;execution&gt;
*             &lt;phase&gt;package&lt;/phase&gt;
*             &lt;goals&gt;
*                 &lt;goal&gt;shade&lt;/goal&gt;
*             &lt;/goals&gt;
*             &lt;configuration&gt;
*                 &lt;relocations&gt;
*                     &lt;relocation&gt;
*                         &lt;pattern&gt;de.slikey&lt;/pattern&gt;
*                         &lt;shadedPattern&gt;org.myplugin.slikey&lt;/shadedPattern&gt;
*                     &lt;/relocation&gt;
*                 &lt;/relocations&gt;
*             &lt;/configuration&gt;
*         &lt;/execution&gt;
*     &lt;/executions&gt;
* &lt;/plugin&gt;
* </pre>
*
* Once shaded, you can instantiate an EffectManager directly, passing it your Plugin
* instance.
*
* \section plugin_sec Unshaded Usage
*
* 1. Get the instance of EffectLib first:
* <i>EffectLib lib = getEffectLib(); // See below</i>
* 2. Create a new EffectManager to handle your effects:
* <i>EffectManager em = new EffectManager(lib);</i>
* 3. Create a new Effect and add start it:
* <i>new BleedEntityEffect(em, player);</i>
* 4. Dispose the EffectManager after creating the last effect:
* <i>em.disposeOnTermination();</i>
*
* If you wish to softdepend to EffectLib, make sure to not use any of the effect classes
* unless you know the EffectLib plugin is loaded. Make sure you're not building the Lib
* into your plugin, it should always be referenced externally (e.g. "provided" in Maven).
*
* <pre>
*       public EffectLib getEffectLib() {
*           Plugin effectLib = Bukkit.getPluginManager().getPlugin("EffectLib");
* 		    if (effectLib == null || !(effectLib instanceof EffectLib)) {
* 			    return null;
* 		    }
*           return (EffectLib)effectLib;
*       }
* </pre>
* 
*/
public final class EffectLib {

	private static EffectLib instance;

	public static EffectLib instance() {
		return instance;
	}

	private EntityManager entityManager;

	public EffectLib() {
		instance = this;
		entityManager = new EntityManager(this);
		EffectManager.initialize();

		loadListeners();
	}

	public List<EffectManager> getEffectManagers() {
		return EffectManager.getManagers();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	private void loadListeners() {
		Bukkit.getServer().getPluginManager().registerEvents(new ItemListener(), BadBlockHub.getInstance());
	}

	public void onDisable() {
		entityManager.dispose();
		EffectManager.disposeAll();
	}

}
