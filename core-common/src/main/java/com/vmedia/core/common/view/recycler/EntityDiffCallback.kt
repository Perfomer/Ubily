package com.vmedia.core.common.view.recycler

import com.vmedia.core.data.KeyEntity

open class EntityDiffCallback<Item : KeyEntity<*>>(
    oldItems: List<Item>,
    newItems: List<Item>
) : ItemDiffCallback<Item>(oldItems, newItems, { it.id!! })