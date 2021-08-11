package de.example.smells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import de.example.model.ElementType;
import de.example.model.ReferenceableType;
import de.example.utils.ArchimateModelUtil;

public class RedundantRelationship extends Detector {
    private final List<String> allowedRelationshipTypes;

    public RedundantRelationship(List<String> relationshipTypes) {
        super("Redundant Relationship");

        this.allowedRelationshipTypes = relationshipTypes;
    }

    @Override
    public List<EASmell> detect() {
        if (model == null) return Collections.emptyList();

        Map<ReferenceableType, List<ReferenceableType>> modelMap
                = ArchimateModelUtil.getModelMap(model, allowedRelationshipTypes, true);

        for (Map.Entry<ReferenceableType, List<ReferenceableType>> modelMapEntrySet : modelMap.entrySet()) {
            Map.Entry pair = modelMapEntrySet;
            List<ReferenceableType> targetElements = (List<ReferenceableType>) pair.getValue();
            ReferenceableType sourceElement = (ReferenceableType) pair.getKey();

            List<ReferenceableType> duplicatedElements = new ArrayList<>();
            duplicatedElements.add(sourceElement);
            duplicatedElements.addAll(targetElements);

            for (Map.Entry<ReferenceableType, List<ReferenceableType>> innerModelMapEntrySet : modelMap.entrySet()) {
                Map.Entry innerPair = innerModelMapEntrySet;
                ReferenceableType innerSourceElement = (ReferenceableType) innerPair.getKey();
                List<ReferenceableType> innerTargetElements = (List<ReferenceableType>) innerPair.getValue();

                if (innerTargetElements.containsAll(duplicatedElements)) {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (ReferenceableType valueElement : duplicatedElements) {
                        stringBuilder.append("'").append(ArchimateModelUtil.getName(valueElement)).append("',");
                    }

                    stringBuilder.append(" targets ").append("'");

                    if (duplicatedElements.contains(innerSourceElement)) {
                        stringBuilder.append(ArchimateModelUtil.getName(sourceElement));
                    } else {
                        stringBuilder.append(ArchimateModelUtil.getName(innerSourceElement));
                    }
                    stringBuilder.append("'.");
                    addToSmells(new EASmell(getSmellName(), (ElementType) innerSourceElement, stringBuilder.toString()));
                }
            }
        }

        return result;
    }

}
