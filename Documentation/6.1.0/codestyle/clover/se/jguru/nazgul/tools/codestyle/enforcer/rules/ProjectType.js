var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":334,"id":122,"methods":[{"el":170,"sc":5,"sl":155},{"el":182,"sc":5,"sl":180},{"el":195,"sc":5,"sl":193},{"el":208,"sc":5,"sl":206},{"el":235,"sc":5,"sl":218},{"el":304,"sc":5,"sl":247},{"el":333,"sc":5,"sl":310}],"name":"ProjectType","sl":39}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateParsingProjectTypes","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":266},{"sl":268},{"sl":273},{"sl":275},{"sl":277},{"sl":278},{"sl":284},{"sl":285},{"sl":295},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_100":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateTestProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_11":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateIgnoreEvaluationOnPomPackaging","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":275},{"sl":277},{"sl":278},{"sl":284},{"sl":285},{"sl":295},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_12":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnParentPomWithModules","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":268},{"sl":269},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_14":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateNoEnforcementOfArtifactsWithIgnoredGroupIDs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_15":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnFindingPocDependenciesInAPIs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_16":{"methods":[{"sl":180},{"sl":193}],"name":"validateIntegrationTestProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_17":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnReactorProjectWithDependencies","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":275},{"sl":277},{"sl":278},{"sl":279},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_18":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnFindingPocDependenciesInAPIs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_19":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateTestProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_2":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateAspectPom","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_20":{"methods":[{"sl":180},{"sl":193}],"name":"validateExampleProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_21":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnCompileScopeImplementationDependency","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_24":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateIgnoreEvaluationOnEarPackaging","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_25":{"methods":[{"sl":155},{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateCorrectPom","pass":true,"statements":[{"sl":159},{"sl":160},{"sl":163},{"sl":164},{"sl":167},{"sl":168},{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":268},{"sl":273},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_26":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnFindingTestUtilityDependenciesInAPIs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_27":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnExecutingEnforcementRuleOnUnknownProjectType","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":315},{"sl":317},{"sl":318},{"sl":326},{"sl":327}]},"test_28":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateIgnoreEvaluationOnEarPackaging","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_29":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnIncorrectProjectTypeSpecification","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":315},{"sl":317},{"sl":318},{"sl":326},{"sl":327}]},"test_3":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateReactorProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_30":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateModelProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_31":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnParentProjectWithModules","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":268},{"sl":269},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_32":{"methods":[{"sl":180},{"sl":193}],"name":"validateIntegrationTestProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_33":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateNoExceptionOnTestScopeDependency","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_35":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateIgnoreEvaluationOnWarPackaging","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_36":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnCompileScopeImplementationDependencyInSpi","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_38":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateNoEvaluationIfProjectIsNotIncluded","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_39":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateAssemblyProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_4":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnCompileScopeImplementationDependencyInImpl","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_40":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateIgnoreEvaluationOnPomPackaging","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":275},{"sl":277},{"sl":278},{"sl":284},{"sl":285},{"sl":295},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_41":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnCompileScopeImplementationDependencyInSpi","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_42":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validatePluginProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_47":{"methods":[{"sl":180},{"sl":193}],"name":"validateExampleProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_48":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateIgnoreEvaluationOnWarPackaging","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_5":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnParentPomWithModules","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":268},{"sl":269},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_50":{"methods":[{"sl":180},{"sl":193}],"name":"validateCodestyleProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_55":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateImplementationProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_56":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnFindingTestUtilityDependenciesInAPIs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_57":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnReactorProjectWithDependencies","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":275},{"sl":277},{"sl":278},{"sl":279},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_58":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateTestProjectTypePom","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_59":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateAspectPom","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_60":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateNoEnforcementOfArtifactsWithIgnoredGroupIDs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_62":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateReactorProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_63":{"methods":[{"sl":155},{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateCorrectPom","pass":true,"statements":[{"sl":159},{"sl":160},{"sl":163},{"sl":164},{"sl":167},{"sl":168},{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":268},{"sl":273},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_64":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateTestProjectTypePom","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_66":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateImplementationProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_67":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionMessage","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_69":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnFindingApplicationDependenciesInAPIs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_71":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnParentProjectWithModules","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":268},{"sl":269},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_73":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExclusionOfGroupIDsDoesNotYieldExceptionWhenIllegalImportsExist","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_74":{"methods":[{"sl":180},{"sl":193}],"name":"validatePocProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_76":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionMessage","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_79":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateNoEvaluationIfProjectIsNotIncluded","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_80":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateModelProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_81":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateApiProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_83":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateNoExceptionOnTestScopeDependency","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_84":{"methods":[{"sl":180},{"sl":193}],"name":"validatePocProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_85":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnExecutingEnforcementRuleOnUnknownProjectType","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":315},{"sl":317},{"sl":318},{"sl":326},{"sl":327}]},"test_86":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validatePluginProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_87":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateApiProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_89":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateSpiProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_9":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExceptionOnIncorrectProjectTypeSpecification","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":315},{"sl":317},{"sl":318},{"sl":326},{"sl":327}]},"test_90":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnCompileScopeImplementationDependencyInImpl","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_92":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnFindingApplicationDependenciesInAPIs","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_93":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateParsingProjectTypes","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":265},{"sl":266},{"sl":268},{"sl":273},{"sl":275},{"sl":277},{"sl":278},{"sl":284},{"sl":285},{"sl":295},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_94":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateSpiProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]},"test_95":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":218},{"sl":247},{"sl":310}],"name":"validateExceptionOnCompileScopeImplementationDependency","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":221},{"sl":225},{"sl":234},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_97":{"methods":[{"sl":180},{"sl":193}],"name":"validateCodestyleProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194}]},"test_98":{"methods":[{"sl":180},{"sl":193},{"sl":206},{"sl":247},{"sl":310}],"name":"validateExclusionOfGroupIDsDoesNotYieldExceptionWhenIllegalImportsExist","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207},{"sl":250},{"sl":254},{"sl":263},{"sl":264},{"sl":298},{"sl":299},{"sl":303},{"sl":315},{"sl":317},{"sl":318},{"sl":321},{"sl":326},{"sl":329},{"sl":332}]},"test_99":{"methods":[{"sl":180},{"sl":193},{"sl":206}],"name":"validateAssemblyProjectPatterns","pass":true,"statements":[{"sl":181},{"sl":194},{"sl":207}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [63, 25], [], [], [], [63, 25], [63, 25], [], [], [63, 25], [63, 25], [], [], [63, 25], [63, 25], [], [], [], [], [], [], [], [], [], [], [], [89, 93, 40, 36, 84, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 39, 28, 92, 32, 50, 1, 86, 90, 62, 66, 71, 35, 21, 41, 74, 60, 94, 100, 29, 98, 79, 85, 3, 30, 87, 42, 20, 56, 55, 47, 63, 31, 5, 19, 81, 17, 16, 38, 58, 26, 64, 80, 27, 9, 25, 97, 57, 24, 73, 15, 18, 11, 14, 99], [89, 93, 40, 36, 84, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 39, 28, 92, 32, 50, 1, 86, 90, 62, 66, 71, 35, 21, 41, 74, 60, 94, 100, 29, 98, 79, 85, 3, 30, 87, 42, 20, 56, 55, 47, 63, 31, 5, 19, 81, 17, 16, 38, 58, 26, 64, 80, 27, 9, 25, 97, 57, 24, 73, 15, 18, 11, 14, 99], [], [], [], [], [], [], [], [], [], [], [], [89, 93, 40, 36, 84, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 39, 28, 92, 32, 50, 1, 86, 90, 62, 66, 71, 35, 21, 41, 74, 60, 94, 100, 29, 98, 79, 85, 3, 30, 87, 42, 20, 56, 55, 47, 63, 31, 5, 19, 81, 17, 16, 38, 58, 26, 64, 80, 27, 9, 25, 97, 57, 24, 73, 15, 18, 11, 14, 99], [89, 93, 40, 36, 84, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 39, 28, 92, 32, 50, 1, 86, 90, 62, 66, 71, 35, 21, 41, 74, 60, 94, 100, 29, 98, 79, 85, 3, 30, 87, 42, 20, 56, 55, 47, 63, 31, 5, 19, 81, 17, 16, 38, 58, 26, 64, 80, 27, 9, 25, 97, 57, 24, 73, 15, 18, 11, 14, 99], [], [], [], [], [], [], [], [], [], [], [], [89, 93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 39, 28, 92, 1, 86, 90, 62, 66, 71, 35, 21, 41, 60, 94, 100, 29, 98, 79, 85, 3, 30, 87, 42, 56, 55, 63, 31, 5, 19, 81, 17, 38, 58, 26, 64, 80, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14, 99], [89, 93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 39, 28, 92, 1, 86, 90, 62, 66, 71, 35, 21, 41, 60, 94, 100, 29, 98, 79, 85, 3, 30, 87, 42, 56, 55, 63, 31, 5, 19, 81, 17, 38, 58, 26, 64, 80, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14, 99], [], [], [], [], [], [], [], [], [], [], [36, 95, 4, 76, 67, 69, 92, 90, 21, 41, 56, 26, 15, 18], [], [], [36, 95, 4, 76, 67, 69, 92, 90, 21, 41, 56, 26, 15, 18], [], [], [], [36, 95, 4, 76, 67, 69, 92, 90, 21, 41, 56, 26, 15, 18], [], [], [], [], [], [], [], [], [36, 95, 4, 76, 67, 69, 92, 90, 21, 41, 56, 26, 15, 18], [], [], [], [], [], [], [], [], [], [], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [], [], [], [], [], [], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 98, 79, 56, 63, 31, 5, 17, 38, 58, 26, 64, 25, 57, 24, 73, 15, 18, 11, 14], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 98, 79, 56, 63, 31, 5, 17, 38, 58, 26, 64, 25, 57, 24, 73, 15, 18, 11, 14], [93, 12, 1, 71, 63, 31, 5, 25], [93, 1], [], [93, 12, 1, 71, 63, 31, 5, 25], [12, 71, 31, 5], [], [], [], [93, 1, 63, 25], [], [93, 40, 1, 17, 57, 11], [], [93, 40, 1, 17, 57, 11], [93, 40, 1, 17, 57, 11], [17, 57], [], [], [], [], [93, 40, 1, 11], [93, 40, 1, 11], [], [], [], [], [], [], [], [], [], [93, 40, 1, 11], [], [], [93, 36, 83, 33, 95, 4, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 35, 21, 41, 60, 98, 79, 56, 38, 58, 26, 64, 24, 73, 15, 18, 14], [93, 36, 83, 33, 95, 4, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 35, 21, 41, 60, 98, 79, 56, 38, 58, 26, 64, 24, 73, 15, 18, 14], [], [], [], [93, 40, 36, 83, 33, 95, 4, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 35, 21, 41, 60, 98, 79, 56, 63, 38, 58, 26, 64, 25, 24, 73, 15, 18, 11, 14], [], [], [], [], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [], [], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 98, 79, 56, 63, 31, 5, 17, 38, 58, 26, 64, 25, 57, 24, 73, 15, 18, 11, 14], [], [], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 29, 98, 79, 85, 56, 63, 31, 5, 17, 38, 58, 26, 64, 27, 9, 25, 57, 24, 73, 15, 18, 11, 14], [29, 85, 27, 9], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 98, 79, 56, 63, 31, 5, 17, 38, 58, 26, 64, 25, 57, 24, 73, 15, 18, 11, 14], [], [], [93, 40, 36, 83, 33, 95, 4, 12, 76, 48, 67, 2, 69, 59, 28, 92, 1, 90, 71, 35, 21, 41, 60, 98, 79, 56, 63, 31, 5, 17, 38, 58, 26, 64, 25, 57, 24, 73, 15, 18, 11, 14], [], []]