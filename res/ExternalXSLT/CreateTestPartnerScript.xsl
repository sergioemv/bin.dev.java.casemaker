<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">

	<xsl:output method="text" version="1.0" encoding="iso-8859-1" indent="no"/>
    <xsl:param name = "filePath" >"c:\XSLTTest\S001.csv"</xsl:param>
    <!-- Script prefix -->
    <xsl:variable name="Prefix">' TestPartner Script Automatically generated by CaseMaker

Sub Main()
    Playback.Logging = False
    OpenURL "http://www.casemaker.de/CarConfigurator/web/"
      
    Dim TestFile As String
    TestFile = Environ("TESTDATAFILE")
    TestFile = Mid(TestFile, 2, Len(TestFile) - 2)
    TestFile = "<xsl:value-of select = "$filePath" />"
    
    Dim Testdata As Collection
    Dim Row As Collection
    
    Set Testdata = CSV_rows(TestFile)
    
    For Each Row In Testdata
          
        ShowTextBox Row("TC")
        TestLog.Comment Row("TC")
    
        Window("Application=iexplore.exe").Attach
            HTMLBrowser("Application=iexplore.exe").TextSelect "MODEL"  
            HTMLRadioButton("Name=model Value=Centeras").Click  
</xsl:variable>

<!-- Script Suffix -->
<xsl:variable name="Suffix">
    Next Row
    Window("Application=iexplore.exe").Close
End Sub	
</xsl:variable>

	<!-- Generate the script -->
	<xsl:template match="/">
		<xsl:value-of select="$Prefix" /> 
		<xsl:apply-templates select="//collection[@field='m_StructureTestData' and @id='i1']" /> 
		<xsl:value-of select="$Suffix" />
	</xsl:template>
	
	<!-- HTMLRadioButton -->
	<xsl:template match="//default[string/@value='HTMLRadioButton']">
		<xsl:text>            HTMLRadioButton("Name='</xsl:text>
		<xsl:apply-templates select="string" mode="ControlName"/>
		<xsl:text>' Value=" &amp; Row("</xsl:text>
		<xsl:apply-templates select="string" mode="Field"/>
		<xsl:text>")).Click
</xsl:text>
	</xsl:template>
	
	<!-- HTMLAnchor -->
	<xsl:template match="//default[string/@value='HTMLAnchor']">
		<xsl:text>            HTMLAnchor("Caption='</xsl:text>
		<xsl:text>" &amp; Row("</xsl:text>
		<xsl:apply-templates select="string" mode="Field"/>
		<xsl:text>")).Click
</xsl:text>
	</xsl:template>

	<!-- HTMLCheckBox -->
	<xsl:template match="//default[string/@value='HTMLCheckBox']">
		<xsl:text>            HTMLCheckBox("Value=*'</xsl:text>
		<xsl:text>" &amp; Row("</xsl:text>
		<xsl:apply-templates select="string" mode="Field"/>
		<xsl:text>") &amp; "'").SetCheck true
</xsl:text>
	</xsl:template>
		
	<!-- Get the TestPartner Command -->
	<xsl:template match="string[@field='stateNameOT']" mode="Command">
		<xsl:value-of select="normalize-space(@value)" />
	</xsl:template>
	
	<!-- Get the TestPartner Control Name -->
	<xsl:template match="string[@field='name']" mode="ControlName">
		<xsl:value-of select="normalize-space(@value)" />
	</xsl:template>

	<!-- Get the test data field name -->
	<xsl:template match="string[@field='field']" mode="Field">
		<xsl:value-of select="normalize-space(@value)" />
	</xsl:template>

</xsl:stylesheet>