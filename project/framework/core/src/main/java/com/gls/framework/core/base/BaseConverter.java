package com.gls.framework.core.base;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author george
 */
public abstract class BaseConverter<Source, Target> {

    //====sourceToTarget====

    public Set<Target> sourceToTarget(Set<Source> sources) {
        if (sources == null) {
            return null;
        } else {
            return sources.stream().map(this::sourceToTarget).collect(Collectors.toSet());
        }
    }

    public List<Target> sourceToTarget(List<Source> sources) {
        if (sources == null) {
            return null;
        } else {
            return sources.stream().map(this::sourceToTarget).collect(Collectors.toList());
        }
    }

    public Collection<Target> sourceToTarget(Collection<Source> sources) {
        if (sources == null) {
            return null;
        } else {
            return sources.stream().map(this::sourceToTarget).collect(Collectors.toSet());
        }
    }

    public Target sourceToTarget(Source source) {
        if (source == null) {
            return null;
        } else {
            return copySourceToTarget(source);
        }
    }

    /**
     * Source to Target
     *
     * @param source
     * @return
     */
    protected abstract Target copySourceToTarget(Source source);

    //====targetToSource====

    public Set<Source> targetToSource(Set<Target> targets) {
        if (targets == null) {
            return null;
        } else {
            return targets.stream().map(this::targetToSource).collect(Collectors.toSet());
        }
    }

    public List<Source> targetToSource(List<Target> targets) {
        if (targets == null) {
            return null;
        } else {
            return targets.stream().map(this::targetToSource).collect(Collectors.toList());
        }
    }

    public Collection<Source> targetToSource(Collection<Target> targets) {
        if (targets == null) {
            return null;
        } else {
            return targets.stream().map(this::targetToSource).collect(Collectors.toSet());
        }
    }

    public Source targetToSource(Target target) {
        if (target == null) {
            return null;
        } else {
            return copyTargetToSource(target);
        }
    }

    /**
     * Target to Source
     *
     * @param target
     * @return
     */
    protected abstract Source copyTargetToSource(Target target);
}
