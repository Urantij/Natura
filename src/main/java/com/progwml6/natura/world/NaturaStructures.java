package com.progwml6.natura.world;

import com.google.common.collect.ImmutableList;
import com.progwml6.natura.common.NaturaModule;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.world.block.RedwoodType;
import com.progwml6.natura.world.block.TreeType;
import com.progwml6.natura.world.worldgen.trees.SupplierBlockStateProvider;
import com.progwml6.natura.world.worldgen.trees.config.BaseOverworldTreeFeatureConfig;
import com.progwml6.natura.world.worldgen.trees.config.RedwoodTreeFeatureConfig;
import com.progwml6.natura.world.worldgen.trees.feature.EucalyptusTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.HopseedTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.OverworldTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.RedwoodTreeFeature;
import com.progwml6.natura.world.worldgen.trees.feature.WillowTreeFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.Logger;

import java.util.OptionalInt;

/**
 * Contains any logic relevant to structure generation, including trees
 */
@SuppressWarnings("unused")
public final class NaturaStructures extends NaturaModule {

  static final Logger log = Util.getLogger("natura_structures");

  /*
   * Misc
   */
  public static final RegistryObject<BlockStateProviderType<SupplierBlockStateProvider>> supplierBlockstateProvider = BLOCK_STATE_PROVIDER_TYPES
    .register("supplier_state_provider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

  /*
   * Features
   */
  public static final RegistryObject<Feature<BaseTreeFeatureConfig>> OVERWORLD_TREE_FEATURE = FEATURES
    .register("overworld_tree", () -> new OverworldTreeFeature(BaseTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<BaseOverworldTreeFeatureConfig>> WILLOW_TREE_FEATURE = FEATURES
    .register("willow_tree", () -> new WillowTreeFeature(BaseOverworldTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<BaseOverworldTreeFeatureConfig>> EUCALYPTUS_TREE_FEATURE = FEATURES
    .register("eucalyptus_tree", () -> new EucalyptusTreeFeature(BaseOverworldTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<BaseOverworldTreeFeatureConfig>> HOPSEED_TREE_FEATURE = FEATURES
    .register("hopseed_tree", () -> new HopseedTreeFeature(BaseOverworldTreeFeatureConfig.CODEC));
  public static final RegistryObject<Feature<RedwoodTreeFeatureConfig>> REDWOOD_TREE_FEATURE = FEATURES
    .register("redwood_tree", () -> new RedwoodTreeFeature(RedwoodTreeFeatureConfig.CODEC));

  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SILVERBELL_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> AMARANTH_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> TIGER_TREE;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SAKURA_TREE;

  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> WILLOW_TREE;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> EUCALYPTUS_TREE;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> HOPSEED_TREE;

  public static ConfiguredFeature<RedwoodTreeFeatureConfig, ?> REDWOOD_TREE;

  // Bee Trees!
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SILVERBELL_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> AMARANTH_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> TIGER_TREE_005;
  public static ConfiguredFeature<BaseTreeFeatureConfig, ?> SAKURA_TREE_005;

  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> WILLOW_TREE_005;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> EUCALYPTUS_TREE_005;
  public static ConfiguredFeature<BaseOverworldTreeFeatureConfig, ?> HOPSEED_TREE_005;

  public static ConfiguredFeature<?, ?> TREES_JUNGLE;
  public static ConfiguredFeature<?, ?> TREES_FOREST;
  public static ConfiguredFeature<?, ?> TREES_SWAMP;


  /**
   * Feature configuration
   *
   * PLACEMENT MOVED TO WorldEvents#onBiomeLoad
   */
  @SubscribeEvent
  void commonSetup(FMLCommonSetupEvent event) {
    MAPLE_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("maple_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration(
      (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.MAPLE).getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.MAPLE).getDefaultState()),
        new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
        new StraightTrunkPlacer(4, 2, 0),
        new TwoLayerFeature(1, 0, 1)))
        .setIgnoreVines().build())
    );

    SILVERBELL_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("silverbell_tree"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.SILVERBELL).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.SILVERBELL).getDefaultState()),
          new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
          new StraightTrunkPlacer(4, 2, 0),
          new TwoLayerFeature(1, 0, 1)))
          .setIgnoreVines().build())
    );

    AMARANTH_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("amaranth_tree"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.AMARANTH).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.AMARANTH).getDefaultState()),
          new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
          new StraightTrunkPlacer(9, 8, 0),
          new TwoLayerFeature(1, 0, 1)))
          .setIgnoreVines().build())
    );

    TIGER_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("tiger_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration(
      (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.TIGER).getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.TIGER).getDefaultState()),
        new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3),
        new StraightTrunkPlacer(6, 4, 0),
        new TwoLayerFeature(1, 0, 1)))
        .setIgnoreVines().build())
    );

    WILLOW_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("willow_tree"), WILLOW_TREE_FEATURE.get().withConfiguration((
      new BaseOverworldTreeFeatureConfig.Builder(
        new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.WILLOW).getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.WILLOW).getDefaultState()),
        5,
        4))
      .build())
    );

    EUCALYPTUS_TREE = Registry
      .register(WorldGenRegistries.CONFIGURED_FEATURE, location("eucalyptus_tree"), EUCALYPTUS_TREE_FEATURE.get().withConfiguration((
        new BaseOverworldTreeFeatureConfig.Builder(
          new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.EUCALYPTUS).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.EUCALYPTUS).getDefaultState()),
          6,
          3))
        .build())
      );

    HOPSEED_TREE = Registry
      .register(WorldGenRegistries.CONFIGURED_FEATURE, location("hopseed_tree"), HOPSEED_TREE_FEATURE.get().withConfiguration((
        new BaseOverworldTreeFeatureConfig.Builder(
          new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.HOPSEED).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.HOPSEED).getDefaultState()),
          2,
          3))
        .build())
      );

    SAKURA_TREE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("sakura_tree"), OVERWORLD_TREE_FEATURE.get().withConfiguration(
      (new BaseTreeFeatureConfig.Builder(new SupplierBlockStateProvider(() -> NaturaWorld.logs.get(TreeType.SAKURA).getDefaultState()),
        new SupplierBlockStateProvider(() -> NaturaWorld.leaves.get(TreeType.SAKURA).getDefaultState()),
        new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4),
        new FancyTrunkPlacer(3, 11, 0),
        new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))))
        .setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build())
    );

    REDWOOD_TREE = Registry
      .register(WorldGenRegistries.CONFIGURED_FEATURE, location("redwood_tree"), REDWOOD_TREE_FEATURE.get().withConfiguration((
        new RedwoodTreeFeatureConfig.Builder(
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood.get(RedwoodType.BARK).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood.get(RedwoodType.HEART).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood.get(RedwoodType.ROOT).getDefaultState()),
          new SupplierBlockStateProvider(() -> NaturaWorld.redwood_leaves.get().getDefaultState()),
          80,
          60,
          12,
          4,
          0.618D,
          0.381D,
          1.0D,
          0.5D))
        .build())
      );

    // Bee Trees
    MAPLE_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("maple_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(MAPLE_TREE.getConfig().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    SILVERBELL_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("silverbell_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(SILVERBELL_TREE.getConfig().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    AMARANTH_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("amaranth_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(AMARANTH_TREE.getConfig().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    TIGER_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("tiger_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(TIGER_TREE.getConfig().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    SAKURA_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("sakura_tree_005"), OVERWORLD_TREE_FEATURE.get()
      .withConfiguration(SAKURA_TREE.getConfig().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    WILLOW_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("willow_tree_005"),
      WILLOW_TREE_FEATURE.get().withConfiguration(WILLOW_TREE.getConfig().withDecorators(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    EUCALYPTUS_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("eucalyptus_tree_005"), EUCALYPTUS_TREE_FEATURE.get()
      .withConfiguration(EUCALYPTUS_TREE.getConfig().withDecorators(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    HOPSEED_TREE_005 = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("hopseed_tree_005"), HOPSEED_TREE_FEATURE.get()
      .withConfiguration(HOPSEED_TREE.getConfig().withDecorators(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));

    // Used for world gen

    TREES_JUNGLE = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("trees_jungle"),
      Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(AMARANTH_TREE.withChance(0.01F)), AMARANTH_TREE))
        .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
        .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));

    TREES_FOREST = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("trees_forest"), Feature.RANDOM_SELECTOR.withConfiguration(
      new MultipleRandomFeatureConfig(
        ImmutableList.of(SILVERBELL_TREE.withChance(0.7F), AMARANTH_TREE.withChance(0.01F), TIGER_TREE.withChance(0.1F)), SILVERBELL_TREE))
      .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1)))
    );

    TREES_SWAMP = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, location("trees_swamp"),
      Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(WILLOW_TREE.withChance(0.5F)), WILLOW_TREE))
        .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
        .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1))));
  }
}
