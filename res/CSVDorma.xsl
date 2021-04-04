<?xml version="1.0" encoding="iso-8859-1"?>
<!-- edited with XMLSpy v2008 rel. 2 (http://www.altova.com) by svonborries (syacomp) -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tes="testcaseslist.data.report.view.bi" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes">
	<xsl:output method="text"/>
	<xsl:template match="/">
		<xsl:for-each select="tes:TestCaseListReport">
			<xsl:for-each select="TestCases">
				<xsl:for-each select="TestCase">
					<xsl:for-each select="EquivalenceClasses">
						<xsl:for-each select="EquivalenceClass">
							<xsl:for-each select="Name">
								<xsl:value-of select="."/>;</xsl:for-each>
							<xsl:for-each select="Effects">
								<xsl:for-each select="Effect">
									<xsl:for-each select="Name"><xsl:value-of select="."/>;</xsl:for-each>
								</xsl:for-each>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
<xsl:text>
</xsl:text>	
		<xsl:for-each select="tes:TestCaseListReport">
			<xsl:for-each select="TestCases">
				<xsl:for-each select="TestCase">
					<xsl:for-each select="EquivalenceClasses">
						<xsl:for-each select="EquivalenceClass">
							<xsl:for-each select="Value"><xsl:value-of select="."/>;</xsl:for-each>
							<xsl:for-each select="Effects">
								<xsl:for-each select="Effect">
									<xsl:for-each select="Description"><xsl:value-of select="."/>;</xsl:for-each>
								</xsl:for-each>
							</xsl:for-each>
						</xsl:for-each>
						<xsl:text>
</xsl:text>
					</xsl:for-each>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
