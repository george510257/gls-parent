package com.gls.framework.core.base;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author george
 */
public interface BaseConverter<Source, Target> {
    //====sourceToTarget====

    /**
     * source To target Set
     *
     * @param sources
     * @return
     */
    default Set<Target> sourceToTargetSet(Collection<Source> sources) {
        if (ObjectUtils.isEmpty(sources)) {
            return null;
        } else {
            return sources.stream().map(this::sourceToTarget).collect(Collectors.toSet());
        }
    }

    /**
     * source To Target List
     *
     * @param sources
     * @return
     */
    default List<Target> sourceToTargetList(Collection<Source> sources) {
        if (ObjectUtils.isEmpty(sources)) {
            return null;
        } else {
            return sources.stream().map(this::sourceToTarget).collect(Collectors.toList());
        }
    }

    /**
     * source To Target
     *
     * @param source
     * @return
     */
    default Target sourceToTarget(Source source) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        } else {
            Target target = getTarget();
            target = copySourceToTarget(source, target);
            return target;
        }
    }

    /**
     * get Target
     *
     * @return
     */
    default Target getTarget() {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<Target> targetClass = (Class<Target>) parameterizedType.getActualTypeArguments()[1];
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * copy Source To Target
     *
     * @param source
     * @param target
     * @return
     */
    Target copySourceToTarget(Source source, Target target);
    //====targetToSource====

    /**
     * target To Source Set
     *
     * @param targets
     * @return
     */
    default Set<Source> targetToSourceSet(Collection<Target> targets) {
        if (ObjectUtils.isEmpty(targets)) {
            return null;
        } else {
            return targets.stream().map(this::targetToSource).collect(Collectors.toSet());
        }
    }

    /**
     * target To Source List
     *
     * @param targets
     * @return
     */
    default List<Source> targetToSourceList(Collection<Target> targets) {
        if (ObjectUtils.isEmpty(targets)) {
            return null;
        } else {
            return targets.stream().map(this::targetToSource).collect(Collectors.toList());
        }
    }

    /**
     * target To Source
     *
     * @param target
     * @return
     */
    default Source targetToSource(Target target) {
        if (ObjectUtils.isEmpty(target)) {
            return null;
        } else {
            Source source = getSource();
            source = copyTargetToSource(target, source);
            return source;
        }
    }

    /**
     * get Source
     *
     * @return
     */
    default Source getSource() {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<Source> sourceClass = (Class<Source>) parameterizedType.getActualTypeArguments()[0];
            return sourceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * copy Target To Source
     *
     * @param target
     * @param source
     * @return
     */
    Source copyTargetToSource(Target target, Source source);
}
