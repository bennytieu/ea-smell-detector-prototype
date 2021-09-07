package de.example.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.example.main.ModelAdapter;
import de.example.model.LangStringType;
import de.example.model.ReferenceableType;
import de.example.model.RelationshipType;

public class ArchimateModelUtil {

    public static String getName(ReferenceableType node) {
        return Optional.of(node)
                       .map(ReferenceableType::getNameGroup)
                       .map(nameGroup -> nameGroup.get(0))
                       .map(LangStringType::getValue)
                       .orElse(null);
    }

    // HashMap representation of an ArchiMate model. The key element has relationships with the value elements.
    public static Map<ReferenceableType, List<ReferenceableType>> getModelMap(
            ModelAdapter model,
            List<String> allowedRelationshipTypes,
            boolean invertRelations) {

        // HashMap representation of the model. The key element has relationships with the value elements.
        Map<ReferenceableType, List<ReferenceableType>> modelMap = new HashMap<>();

        for (RelationshipType relationship : model.getRelationships()) {
            String relationShipType = relationship.getClass().getSimpleName();

            if (allowedRelationshipTypes.isEmpty() || allowedRelationshipTypes.contains(relationShipType)) {
                ReferenceableType source = invertRelations ? (ReferenceableType) relationship.getTarget()
                        : (ReferenceableType) relationship.getSource();
                ReferenceableType target = invertRelations ? (ReferenceableType) relationship.getSource()
                        : (ReferenceableType) relationship.getTarget();

                if (modelMap.get(source) == null) {
                    List<ReferenceableType> targetElements = new ArrayList<>();
                    targetElements.add(target);
                    modelMap.put(source, targetElements);
                } else {
                    modelMap.get(source).add(target);
                }
            }
        }

        return modelMap;
    }
}
