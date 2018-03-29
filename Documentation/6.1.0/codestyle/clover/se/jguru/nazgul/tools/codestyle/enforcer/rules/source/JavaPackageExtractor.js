var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":106,"id":268,"methods":[{"el":60,"sc":9,"sl":56},{"el":78,"sc":5,"sl":69},{"el":97,"sc":5,"sl":80},{"el":105,"sc":5,"sl":102}],"name":"JavaPackageExtractor","sl":37}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_23":{"methods":[{"sl":69},{"sl":80}],"name":"validateExceptionOnSubmittingDirectoriesToPackageExtractor","pass":true,"statements":[{"sl":72},{"sl":81},{"sl":93}]},"test_43":{"methods":[{"sl":69},{"sl":80}],"name":"validateDefaultPackageReturnedOnMalformedPackageStatement","pass":true,"statements":[{"sl":72},{"sl":73},{"sl":77},{"sl":81},{"sl":83},{"sl":84},{"sl":86},{"sl":96}]},"test_44":{"methods":[{"sl":69},{"sl":80}],"name":"validateExceptionOnSubmittingDirectoriesToPackageExtractor","pass":true,"statements":[{"sl":72},{"sl":81},{"sl":93}]},"test_51":{"methods":[{"sl":69},{"sl":80}],"name":"validateDefaultPackageReturnedOnMalformedPackageStatement","pass":true,"statements":[{"sl":72},{"sl":73},{"sl":77},{"sl":81},{"sl":83},{"sl":84},{"sl":86},{"sl":96}]},"test_53":{"methods":[{"sl":56},{"sl":69},{"sl":80},{"sl":102}],"name":"validateExceptionOnIncorrectSourceCodePackaging","pass":true,"statements":[{"sl":58},{"sl":72},{"sl":73},{"sl":81},{"sl":83},{"sl":84},{"sl":86},{"sl":87},{"sl":104}]},"test_61":{"methods":[{"sl":56},{"sl":69},{"sl":80},{"sl":102}],"name":"validatePatternExtraction","pass":true,"statements":[{"sl":58},{"sl":72},{"sl":73},{"sl":81},{"sl":83},{"sl":84},{"sl":86},{"sl":87},{"sl":104}]},"test_68":{"methods":[{"sl":56},{"sl":69},{"sl":80},{"sl":102}],"name":"validatePatternExtraction","pass":true,"statements":[{"sl":58},{"sl":72},{"sl":73},{"sl":81},{"sl":83},{"sl":84},{"sl":86},{"sl":87},{"sl":104}]},"test_70":{"methods":[{"sl":56},{"sl":69},{"sl":80},{"sl":102}],"name":"validateExceptionOnIncorrectSourceCodePackaging","pass":true,"statements":[{"sl":58},{"sl":72},{"sl":73},{"sl":81},{"sl":83},{"sl":84},{"sl":86},{"sl":87},{"sl":104}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [68, 70, 53, 61], [], [68, 70, 53, 61], [], [], [], [], [], [], [], [], [], [], [44, 68, 70, 51, 53, 61, 23, 43], [], [], [44, 68, 70, 51, 53, 61, 23, 43], [68, 70, 51, 53, 61, 43], [], [], [], [51, 43], [], [], [44, 68, 70, 51, 53, 61, 23, 43], [44, 68, 70, 51, 53, 61, 23, 43], [], [68, 70, 51, 53, 61, 43], [68, 70, 51, 53, 61, 43], [], [68, 70, 51, 53, 61, 43], [68, 70, 53, 61], [], [], [], [], [], [44, 23], [], [], [51, 43], [], [], [], [], [], [68, 70, 53, 61], [], [68, 70, 53, 61], [], []]