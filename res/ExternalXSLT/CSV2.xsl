<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">

	<xsl:output method="text" encoding="iso-8859-1" />

	<xsl:template match="/">
		<xsl:apply-templates />

		<xsl:for-each select="/reportCSV/lineReport">

			<xsl:value-of select="@nameTestDataSet" />
			<xsl:text>;</xsl:text>
			<xsl:value-of select="@nameTestCase" />

			<xsl:for-each select="./lineReport2">
				<xsl:text>;</xsl:text>
				<xsl:value-of select="@nameElement" />
				<xsl:text></xsl:text>
			</xsl:for-each>
<xsl:text>
</xsl:text>

		</xsl:for-each>

	</xsl:template>

</xsl:stylesheet>