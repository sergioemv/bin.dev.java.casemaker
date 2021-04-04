<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:tes="testcaselist2.data.report.view.bi" 
xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes"> 
<xsl:output method="text"/>
<xsl:template match="tes:ReportTestCases">
<xsl:text> </xsl:text>
<xsl:value-of select="./Project/Name"/>
<xsl:text>

 </xsl:text>
<xsl:text> 1.1  </xsl:text><xsl:value-of select="./TestObject/Name"/>
<xsl:text>

    </xsl:text>
<xsl:value-of select="./TestObject/Description"/>
<xsl:text>

    </xsl:text>
<xsl:for-each select="./TestCase">  
<xsl:text>1.1.</xsl:text><xsl:value-of select="./Id"/><xsl:text>  </xsl:text><xsl:value-of select="./Name"/>
<xsl:text>
      </xsl:text>
<xsl:value-of select="./Description"/>
<xsl:text>

   </xsl:text>
</xsl:for-each>
</xsl:template>		
</xsl:stylesheet>
