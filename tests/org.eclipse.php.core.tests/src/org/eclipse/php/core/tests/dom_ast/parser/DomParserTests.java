/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła - convert to JUnit4
 *******************************************************************************/
package org.eclipse.php.core.tests.dom_ast.parser;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.ASTParser;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.core.tests.PDTTUtils;
import org.eclipse.php.core.tests.PHPCoreTests;
import org.eclipse.php.core.tests.PdttFile;
import org.eclipse.php.core.tests.TestSuiteWatcher;
import org.eclipse.php.core.tests.runner.PDTTList;
import org.eclipse.php.core.tests.runner.PDTTList.Parameters;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;

@RunWith(PDTTList.class)
public class DomParserTests {

	@ClassRule
	public static TestWatcher watcher = new TestSuiteWatcher();

	@Parameters
	public static final Map<PHPVersion, String[]> TESTS = new LinkedHashMap<>();

	static {
		TESTS.put(PHPVersion.PHP5, new String[] { "/workspace/dom_parser/php5" });
		TESTS.put(PHPVersion.PHP5_3, new String[] { "/workspace/dom_parser/php53",
				"/workspace/dom_parser/php53/php5only", "/workspace/dom_parser/php53/phplowerthan73" });
		TESTS.put(PHPVersion.PHP5_4,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php53/php5only",
						"/workspace/dom_parser/php54", "/workspace/dom_parser/php53/phplowerthan73" });
		TESTS.put(PHPVersion.PHP5_5,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php53/php5only",
						"/workspace/dom_parser/php54", "/workspace/dom_parser/php55",
						"/workspace/dom_parser/php53/phplowerthan73" });
		TESTS.put(PHPVersion.PHP5_6,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php53/php5only",
						"/workspace/dom_parser/php54", "/workspace/dom_parser/php55", "/workspace/dom_parser/php56",
						"/workspace/dom_parser/php53/phplowerthan73" });
		TESTS.put(PHPVersion.PHP7_0,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php53/phplowerthan73" });
		TESTS.put(PHPVersion.PHP7_1,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71", "/workspace/dom_parser/php53/phplowerthan73" });
		TESTS.put(PHPVersion.PHP7_2,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71", "/workspace/dom_parser/php72",
						"/workspace/dom_parser/php53/phplowerthan73" });
		TESTS.put(PHPVersion.PHP7_3,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71", "/workspace/dom_parser/php72", "/workspace/dom_parser/php73" });
		TESTS.put(PHPVersion.PHP7_4,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71", "/workspace/dom_parser/php72", "/workspace/dom_parser/php73",
						"/workspace/dom_parser/php74" });

		TESTS.put(PHPVersion.PHP8_0,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71", "/workspace/dom_parser/php72", "/workspace/dom_parser/php73",
						"/workspace/dom_parser/php74", "/workspace/dom_parser/php80" });
		TESTS.put(PHPVersion.PHP8_1,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71", "/workspace/dom_parser/php72", "/workspace/dom_parser/php73",
						"/workspace/dom_parser/php74", "/workspace/dom_parser/php80", "/workspace/dom_parser/php81" });
		TESTS.put(PHPVersion.PHP8_2,
				new String[] { "/workspace/dom_parser/php53", "/workspace/dom_parser/php54",
						"/workspace/dom_parser/php55", "/workspace/dom_parser/php56", "/workspace/dom_parser/php7",
						"/workspace/dom_parser/php71", "/workspace/dom_parser/php72", "/workspace/dom_parser/php73",
						"/workspace/dom_parser/php74", "/workspace/dom_parser/php80", "/workspace/dom_parser/php81",
						"/workspace/dom_parser/php82" });
	};

	private ASTParser parser;

	public DomParserTests(PHPVersion phpVersion, String fileNames[]) {
		parser = ASTParser.newParser(phpVersion, ProjectOptions.isSupportingASPTags((IProject) null),
				ProjectOptions.useShortTags((IProject) null));
	}

	@Test
	public void parserTest(String fileName) throws Exception {
		PdttFile file = new PdttFile(PHPCoreTests.getDefault().getBundle(), fileName, "UTF-8");

		parser.setSource(file.getFile().trim().toCharArray());
		Program program = parser.createAST(new NullProgressMonitor());

		PDTTUtils.assertContents(file.getExpected(), program.toString());
	}
}
