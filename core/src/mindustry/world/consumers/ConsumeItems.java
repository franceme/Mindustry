package mindustry.world.consumers;

import arc.math.*;
import arc.struct.*;
import arc.scene.ui.layout.*;
import arc.util.ArcAnnotate.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;
import mindustry.world.meta.values.*;

public class ConsumeItems extends Consume{
    public final @NonNull ItemStack[] items;

    public ConsumeItems(ItemStack[] items){
        this.items = items;
    }

    /** Mods.*/
    protected ConsumeItems(){
        this(ItemStack.empty);
    }

    @Override
    public void applyItemFilter(Bits filter){
        for(ItemStack stack : items){
            filter.set(stack.item.id);
        }
    }

    @Override
    public ConsumeType type(){
        return ConsumeType.item;
    }

    @Override
    public void build(Tilec tile, Table table){
        for(ItemStack stack : items){
            table.add(new ReqImage(new ItemImage(stack.item.icon(Cicon.medium), stack.amount),
                () -> tile.items() != null && tile.items().has(stack.item, stack.amount))).size(8 * 4).padRight(Mathf.digits(stack.amount) * 6);
        }
    }

    @Override
    public String getIcon(){
        return "icon-item";
    }

    @Override
    public void update(Tilec entity){

    }

    @Override
    public void trigger(Tilec entity){
        for(ItemStack stack : items){
            entity.items().remove(stack);
        }
    }

    @Override
    public boolean valid(Tilec entity){
        return entity.items() != null && entity.items().has(items);
    }

    @Override
    public void display(BlockStats stats){
        stats.add(booster ? BlockStat.booster : BlockStat.input, new ItemListValue(items));
    }
}
