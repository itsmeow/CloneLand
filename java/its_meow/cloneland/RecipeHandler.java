package its_meow.cloneland;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

public class RecipeHandler {
	//Created just because 1.12 crafting is currently broken.
	
	
	

/**
 * This should only be used for registering recipes for vanilla objects and not mod-specific objects.
 * @param name The name of the recipe. ModID is pre-pended to it.
 * @param stack The output of the recipe.
 * @param recipeComponents The recipe components.
 */
public static void addShapedRecipe(String name, ItemStack stack, Object... recipeComponents)
{	
    name = Ref.MOD_ID + ":" + name;
    String s = "";
    int i = 0;
    int j = 0;
    int k = 0;

    if (recipeComponents[i] instanceof String[])
    {
        String[] astring = (String[])((String[])recipeComponents[i++]);

        for (String s2 : astring)
        {
            ++k;
            j = s2.length();
            s = s + s2;
        }
    }
    else
    {
        while (recipeComponents[i] instanceof String)
        {
            String s1 = (String)recipeComponents[i++];
            ++k;
            j = s1.length();
            s = s + s1;
        }
    }

    Map<Character, ItemStack> map;

    for (map = Maps.<Character, ItemStack>newHashMap(); i < recipeComponents.length; i += 2)
    {
        Character character = (Character)recipeComponents[i];
        ItemStack itemstack = ItemStack.EMPTY;

        if (recipeComponents[i + 1] instanceof Item)
        {
            itemstack = new ItemStack((Item)recipeComponents[i + 1]);
        }
        else if (recipeComponents[i + 1] instanceof Block)
        {
            itemstack = new ItemStack((Block)recipeComponents[i + 1], 1, 32767);
        }
        else if (recipeComponents[i + 1] instanceof ItemStack)
        {
            itemstack = (ItemStack)recipeComponents[i + 1];
        }

        map.put(character, itemstack);
    }

    NonNullList<Ingredient> aitemstack = NonNullList.withSize(j * k, Ingredient.field_193370_a);

    for (int l = 0; l < j * k; ++l)
    {
        char c0 = s.charAt(l);

        if (map.containsKey(Character.valueOf(c0)))
        {
            aitemstack.set(l, Ingredient.func_193369_a(((ItemStack)map.get(Character.valueOf(c0))).copy()));
        }
    }

    ShapedRecipes shapedrecipes = new ShapedRecipes(name, j, k, aitemstack, stack);
	
	CraftingManager.func_193379_a(name, shapedrecipes);
}
	

/**
 * This should only be used for registering recipes for vanilla objects and not mod-specific objects.
 * @param name The name of the recipe.
 * @param stack The output stack.
 * @param recipeComponents The recipe components.
 */
public static void addShapelessRecipe(String name, ItemStack stack, Object... recipeComponents)
{
    name = Ref.MOD_ID + ":" + name;
    NonNullList<Ingredient> list = NonNullList.create();

    for (Object object : recipeComponents)
    {
        if (object instanceof ItemStack)
        {
            list.add(Ingredient.func_193369_a(((ItemStack)object).copy()));
        }
        else if (object instanceof Item)
        {
            list.add(Ingredient.func_193369_a(new ItemStack((Item)object)));
        }
        else
        {
            if (!(object instanceof Block))
            {
                throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
            }

            list.add(Ingredient.func_193369_a(new ItemStack((Block)object)));
        }
    }

    ShapelessRecipes shapelessRecipes = new ShapelessRecipes(name, stack, list);
	
	CraftingManager.func_193379_a(name, shapelessRecipes);
}


}
