<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tes="testdatasetlist.data.report.view.bi" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes">
	<xsl:output method="text"/>
	<xsl:template match="/">
		<xsl:for-each select="tes:ReportTestDataSet">
			<xsl:for-each select="tes:TestDataSet">
				<xsl:for-each select="@Name">
					<xsl:value-of select="."/>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>;<xsl:for-each select="tes:ReportTestDataSet">
			<xsl:for-each select="tes:TestDataSet">
				<xsl:for-each select="@LabelTC">
					<xsl:value-of select="."/>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
		<xsl:for-each select="tes:ReportTestDataSet">
			<xsl:for-each select="tes:TestDataSet">
				<xsl:for-each select="tes:LabelTypeDatasName">
					<xsl:for-each select="@Name">;<xsl:value-of select="."/>
					</xsl:for-each>
				</xsl:for-each>								
			</xsl:for-each>
		</xsl:for-each>
<xsl:text>
</xsl:text>
		<xsl:for-each select="tes:ReportTestDataSet">		
			<xsl:for-each select="tes:TestDataSet">			
				<xsl:for-each select="tes:TestData">
					<xsl:for-each select="@TDNameStructureTDName">
						<xsl:value-of select="."/>
					</xsl:for-each>
					<xsl:for-each select="@TestCaseInTestData">;<xsl:value-of select="."/>
					</xsl:for-each>
					<xsl:for-each select="tes:StructureTestData">
						<xsl:for-each select="tes:TypeData">
							<xsl:for-each select="@Value">;<xsl:value-of select="."/>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>
					<xsl:text>
</xsl:text>
				</xsl:for-each>
			</xsl:for-each>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
