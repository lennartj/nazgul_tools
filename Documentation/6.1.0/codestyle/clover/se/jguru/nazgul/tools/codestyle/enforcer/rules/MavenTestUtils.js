var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":119,"id":329,"methods":[{"el":43,"sc":5,"sl":42},{"el":73,"sc":5,"sl":51},{"el":88,"sc":5,"sl":83},{"el":118,"sc":5,"sl":99}],"name":"MavenTestUtils","sl":35}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_1":{"methods":[{"sl":83},{"sl":99}],"name":"validateParsingProjectTypes","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]},"test_12":{"methods":[{"sl":51}],"name":"validateExceptionOnParentPomWithModules","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_17":{"methods":[{"sl":83},{"sl":99}],"name":"validateExceptionOnReactorProjectWithDependencies","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]},"test_2":{"methods":[{"sl":51}],"name":"validateAspectPom","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_25":{"methods":[{"sl":51}],"name":"validateCorrectPom","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_29":{"methods":[{"sl":83},{"sl":99}],"name":"validateExceptionOnIncorrectProjectTypeSpecification","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]},"test_31":{"methods":[{"sl":83},{"sl":99}],"name":"validateExceptionOnParentProjectWithModules","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]},"test_5":{"methods":[{"sl":51}],"name":"validateExceptionOnParentPomWithModules","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_53":{"methods":[{"sl":51}],"name":"validateExceptionOnIncorrectSourceCodePackaging","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_57":{"methods":[{"sl":83},{"sl":99}],"name":"validateExceptionOnReactorProjectWithDependencies","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]},"test_58":{"methods":[{"sl":51}],"name":"validateTestProjectTypePom","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_59":{"methods":[{"sl":51}],"name":"validateAspectPom","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_63":{"methods":[{"sl":51}],"name":"validateCorrectPom","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_64":{"methods":[{"sl":51}],"name":"validateTestProjectTypePom","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_70":{"methods":[{"sl":51}],"name":"validateExceptionOnIncorrectSourceCodePackaging","pass":true,"statements":[{"sl":53},{"sl":54},{"sl":67},{"sl":68}]},"test_71":{"methods":[{"sl":83},{"sl":99}],"name":"validateExceptionOnParentProjectWithModules","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]},"test_9":{"methods":[{"sl":83},{"sl":99}],"name":"validateExceptionOnIncorrectProjectTypeSpecification","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]},"test_93":{"methods":[{"sl":83},{"sl":99}],"name":"validateParsingProjectTypes","pass":true,"statements":[{"sl":87},{"sl":104},{"sl":105},{"sl":106},{"sl":107},{"sl":108},{"sl":109},{"sl":112},{"sl":113},{"sl":114},{"sl":115},{"sl":116},{"sl":117}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [12, 63, 2, 59, 5, 58, 64, 70, 25, 53], [], [12, 63, 2, 59, 5, 58, 64, 70, 25, 53], [12, 63, 2, 59, 5, 58, 64, 70, 25, 53], [], [], [], [], [], [], [], [], [], [], [], [], [12, 63, 2, 59, 5, 58, 64, 70, 25, 53], [12, 63, 2, 59, 5, 58, 64, 70, 25, 53], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [93, 31, 17, 1, 9, 57, 71, 29], [], [], [], [93, 31, 17, 1, 9, 57, 71, 29], [], [], [], [], [], [], [], [], [], [], [], [93, 31, 17, 1, 9, 57, 71, 29], [], [], [], [], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [], [], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [93, 31, 17, 1, 9, 57, 71, 29], [], []]
