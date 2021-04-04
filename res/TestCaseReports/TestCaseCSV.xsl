<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" 
xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns:tes="testcaselist2.data.report.view.bi" 
xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes"> 
<xsl:output method="text"/>
<xsl:template match="/">
<xsl:value-of select="/tes:ReportTestCases/@LabelCSV"/>
<xsl:text>;</xsl:text>		
<xsl:for-each select="/tes:ReportTestCases/Element/Name">  
   <xsl:value-of select="."/>
   <xsl:text>;</xsl:text>	                                  
</xsl:for-each>
<xsl:text>             
</xsl:text>	                                                            
<xsl:apply-templates select="//TestCase">
</xsl:apply-templates>				
</xsl:template>		
<xsl:template match="TestCase">
<xsl:for-each select=".">
<xsl:value-of select="./Name"/>
<xsl:text>;</xsl:text>	   
<xsl:for-each select="./EqClassReference">
<xsl:variable name="Uid" select ="@UniqueId"/>
<xsl:for-each select="/tes:ReportTestCases/Element/EquivalenceClass [$Uid = @UniqueId]">
<xsl:value-of select="Value"/>
</xsl:for-each>
<xsl:text>;</xsl:text>	                                                                    
</xsl:for-each>
<xsl:text>             
</xsl:text>	         	                                                            
</xsl:for-each>			
</xsl:template>	
</xsl:stylesheet>
