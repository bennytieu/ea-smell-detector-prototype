package de.example.smells;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.example.model.ElementType;
import de.example.model.ReferenceableType;
import de.example.utils.ArchimateModelUtil;

public class MergeConflicts extends Detector {
    private final List<String> allowedRelationshipTypes;
    private final int maxNumRelationships;

    public MergeConflicts(List<String> relationshipTypes, int maxNumRelationships) {
        super("Merge Conflicts");

        this.allowedRelationshipTypes = relationshipTypes;
        this.maxNumRelationships = maxNumRelationships;
    }

    @Override
    public List<EASmell> detect() {
        if (model == null) return Collections.emptyList();

        Map<ReferenceableType, List<ReferenceableType>> modelMap
                = ArchimateModelUtil.getModelMap(model, allowedRelationshipTypes, false);

        Iterator iterator = modelMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();

            ReferenceableType sourceNode = (ReferenceableType) pair.getKey();
            List<ReferenceableType> targetNodes = (List<ReferenceableType>) pair.getValue();

            if (targetNodes.size() >= maxNumRelationships) {
                String sourceValue = ArchimateModelUtil.getName(sourceNode);
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("'").append(sourceValue).append("' ")
                             .append("is having merge conflicts with:");


                for (ReferenceableType targetNode : targetNodes) {
                    String targetValue = ArchimateModelUtil.getName(targetNode);
                    stringBuilder.append("'").append(targetValue).append("'");
                }


                addToSmells(new EASmell(getSmellName(), (ElementType) sourceNode, stringBuilder.toString()));
            }

            iterator.remove();
        }

        return result;
    }

}
