/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.structureTemplatesTutorial.systems;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.event.ReceiveEvent;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.entitySystem.systems.UpdateSubscriberSystem;
import org.terasology.logic.location.LocationComponent;
import org.terasology.logic.players.event.OnPlayerSpawnedEvent;
import org.terasology.math.Side;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.In;
import org.terasology.structureTemplates.events.SpawnStructureEvent;
import org.terasology.structureTemplates.interfaces.StructureTemplateProvider;
import org.terasology.structureTemplates.util.BlockRegionTransform;

@RegisterSystem
public class SpawnShelterSystem extends BaseComponentSystem implements UpdateSubscriberSystem {
    private static final String SHELTER_TEMPLATE_TYPE = "StructureTemplatesTutorial:basicShelter";
    private static final int HEIGHT_OFFSET = 1; //The player is two blocks-tall, so the shelter must be placed at half that height

    @In
    private StructureTemplateProvider templateProvider;

    @Override
    public void initialise() {
    }

    @Override
    public void update(float delta) {

    }


    @ReceiveEvent
    public void onPlayerSpawn(OnPlayerSpawnedEvent event, EntityRef character) {
        EntityRef template = templateProvider.getRandomTemplateOfType(SHELTER_TEMPLATE_TYPE);

        if (template == null) {
            return;
        }

        template.send(new SpawnStructureEvent(BlockRegionTransform.createMovingThenRotating(new Vector3i(0, character.getComponent(LocationComponent.class).getWorldPosition().y - HEIGHT_OFFSET, 0), Side.FRONT, Side.FRONT)));
    }
}
