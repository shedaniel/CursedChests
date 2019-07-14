package ninjaphenix.cursedchests.config;

import blue.endless.jankson.Comment;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class Config
{
    public static Config INSTANCE;

    @Comment("Determines what screen type should be used, either SCROLL or PAGED.")
    public final String screen_type = "SCROLL";

    public static void initialize()
    {
        //ConfigManager.getMarshaller().registerSerializer(Identifier.class, JsonPrimitive::new);
        //ConfigManager.getMarshaller().register(Identifier.class, (it) -> (it instanceof String) ? new Identifier((String) it) : new Identifier(it.toString()));
        //ConfigManager.getMarshaller().registerTypeAdapter(Tier.class, (it) ->
        //{
        //    String name = it.get(String.class, "name");
        //    Integer max_speed = it.get(Integer.class, "max_speed");
        //    Integer xz_range = it.get(Integer.class, "xz_range");
        //    Integer y_range = it.get(Integer.class, "y_range");
        //    return new Tier(name, max_speed < 1 ? 1 : max_speed, xz_range < 0 ? 0 : xz_range, y_range < 0 ? 0 : y_range);
        //});
        Path configDirectory = FabricLoader.getInstance().getConfigDirectory().toPath();
        INSTANCE = ConfigManager.loadConfig(Config.class, configDirectory.resolve("CursedChests.cfg").toFile());
    }

}
