package com.barasher.esng.question;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class QuestionFactory {

    // @Autowired
    // private EsngConfiguration _config;

    private static final Logger LOG = LoggerFactory.getLogger(QuestionFactory.class);
    private static final Random RANDOM = new Random();

    private final Multimap<Integer, IQuestionProvider> _providersByLevel = ArrayListMultimap.create();
    private int _maxLevel = -1;

    public QuestionFactory() throws IllegalStateException {
	// getting all questions
	final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
		false);
	provider.addIncludeFilter(new AnnotationTypeFilter(QuestionProvider.class));
	int curLvl = 0;
	for (final BeanDefinition beanDef : provider.findCandidateComponents("com.barasher")) {
	    try {
		final Class<?> c = Class.forName(beanDef.getBeanClassName());
		if (IQuestionProvider.class.isAssignableFrom(c)) {
		    curLvl = getLevel(c);
		    _providersByLevel.put(curLvl, (IQuestionProvider) c.newInstance());
		    LOG.info("Registering question {} for level {}", c.getCanonicalName(), curLvl);
		    _maxLevel = Math.max(_maxLevel, curLvl);
		} else {
		    throw new IllegalStateException(
			    "Inconstistent QuestionContextProvider : class " + beanDef.getBeanClassName()
				    + " does not implements " + IQuestionProvider.class.getCanonicalName());
		}
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		throw new IllegalStateException(
			"Error while registering QuestionContextProvider " + beanDef.getBeanClassName(), e);
	    }
	}
	if (_providersByLevel.get(0) == null) {
	    throw new IllegalStateException("No QuestionContextProvider found for level 1");
	}

	// rebalancing
	for (int i = 1; i < getMaxLevel(); i++) {
	    _providersByLevel.putAll(i + 1, _providersByLevel.get(i));
	}
    }

    private int getLevel(Class<?> aClass) {
	final QuestionProvider a = aClass.getAnnotation(QuestionProvider.class);
	if (a == null) {
	    throw new IllegalStateException("Erreur while getting level from " + aClass.getCanonicalName());
	}
	return a.level();
    }

    public int getMaxLevel() {
	return _maxLevel;
    }

    static int random(int aIncludedMin, int aExcludedMax) {
	return RANDOM.nextInt(aExcludedMax - aIncludedMin) + aIncludedMin;
    }

    IQuestionProvider getProvided(int aLevel) {
	LOG.info("Asking for a level {} question...", aLevel);
	final Collection<IQuestionProvider> selLevelProviders = _providersByLevel.get(aLevel);
	final Iterator<IQuestionProvider> it = selLevelProviders.iterator();
	final int selProv = random(0, selLevelProviders.size());
	for (int i = 0; i < selProv; i++) {
	    it.next();
	}
	return it.next();
    }

    public QuestionContext build(int aLevel) {
	return getProvided(aLevel).provide();
    }

}
