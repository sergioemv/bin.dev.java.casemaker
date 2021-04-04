<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tes="testcaselist2.data.report.view.bi" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes">
	<xsl:output method="html" encoding="UTF-8"/>
	<xsl:key name="elementNameId" match="/tes:ReportTestCases/Element/Name" use="../Id"/>
	<xsl:key name="equivalenceClassValueId" match="/tes:ReportTestCases/Element/EquivalenceClass/Value" use="../@UniqueId"/>
	<xsl:key name="equivalenceClassEffectId" match="/tes:ReportTestCases/Element/EquivalenceClass/CauseEffects/CauseEffect" use="../../@UniqueId"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>
					<xsl:value-of select="/tes:ReportTestCases/Title/Name"/>
				</title>
				<style>
	{mso-displayed-decimal-separator:&quot;\,&quot;;
	mso-displayed-thousand-separator:&quot;\.&quot;;}
@page
	{margin:.98in .79in .98in .79in;
	mso-header-margin:0in;
	mso-footer-margin:0in;}
tr
	{mso-height-source:auto;}
col
	{mso-width-source:auto;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Arial;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	border:none;
	mso-protection:locked visible;
	mso-style-name:Normal;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding:0px;
	mso-ignore:padding;
	color:windowtext;
	font-size:10.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Arial;
	mso-generic-font-family:auto;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-weight:700;
	font-style:italic;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	text-align:left;
	white-space:normal;}
.xl25
	{mso-style-parent:style0;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	text-align:right;
	vertical-align:top;
	white-space:normal;}
.xl26
	{mso-style-parent:style0;
	font-weight:700;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	white-space:normal;}
.xl27
	{mso-style-parent:style0;
	background:silver;
	mso-pattern:auto none;
	white-space:normal;}
.xl28
	{mso-style-parent:style0;
	font-weight:700;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	text-align:center;
	vertical-align:top;
	border:.5pt solid windowtext;
	background:#FFFFCC;
	mso-pattern:auto none;}
.xl29
	{mso-style-parent:style0;
	font-weight:700;
	font-family:Arial, sans-serif;
	mso-font-charset:0;
	text-align:center;
	vertical-align:top;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:#FFFFCC;
	mso-pattern:auto none;}
.xl30
	{mso-style-parent:style0;
	vertical-align:top;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl31
	{mso-style-parent:style0;
	vertical-align:top;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:none;
	border-left:.5pt solid windowtext;
	white-space:normal;}
.xl32
	{mso-style-parent:style0;
	vertical-align:top;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	white-space:normal;}
--&gt;
</style>
			</head>
			<body link="blue" vlink="purple">
				<xsl:apply-templates select="tes:ReportTestCases"/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="tes:ReportTestCases">
	<!-- HEADERS, TITLE LOGO -->	
		<table border="0" cellpadding="0" cellspacing="0" width="1527" style="border-collapse:collapse;table-layout:fixed;width:1146pt">
			<!-- TITLE -->
			<tr height="17" style="height:12.75pt">
				<td colspan="2" rowspan="2" height="34" class="xl24" width="510" style="height:25.5pt;width:383pt">
					<xsl:value-of select="./@LabelTCL2"/>
				</td>
				<td rowspan="8" height="136" width="320" style="height:102.0pt;width:240pt" align="left" valign="top">
					<span style="mso-ignore:vglayout;position:absolute;z-index:1;margin-left:0px;margin-top:0px;width:400px;height:145px">
						<img width="400" height="145" src="images/cmlogo.jpg"/>
					</span>
					<span style="mso-ignore:vglayout2">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td rowspan="8" height="136" class="xl25" width="320" style="height:102.0pt;width:240pt"/>
							</tr>
						</table>
					</span>
				</td>
				<td width="57" style="width:43pt"/>
				<td width="320" style="width:240pt"/>
				<td width="320" style="width:240pt"/>
			</tr>
			<!-- SPACE -->
			<tr height="17" style="height:12.75pt">
				<td height="17" colspan="3" style="height:12.75pt;mso-ignore:colspan"/>
			</tr>
			<tr height="17" style="height:12.75pt">
				<td height="17" colspan="2" style="height:12.75pt;mso-ignore:colspan"/>
				<td colspan="3" style="mso-ignore:colspan"/>
			</tr>
			<tr height="17" style="height:12.75pt">
				<td height="17" colspan="2" style="height:12.75pt;mso-ignore:colspan"/>
				<td colspan="3" style="mso-ignore:colspan"/>
			</tr>
			<tr height="17" style="height:12.75pt">
				<td height="17" colspan="2" style="height:12.75pt;mso-ignore:colspan"/>
				<td colspan="3" style="mso-ignore:colspan"/>
			</tr>
			<tr height="17" style="height:12.75pt">
				<td height="17" colspan="2" style="height:12.75pt;mso-ignore:colspan"/>
				<td colspan="3" style="mso-ignore:colspan"/>
			</tr>
			<tr height="17" style="height:12.75pt">
				<td height="17" colspan="2" style="height:12.75pt;mso-ignore:colspan"/>
				<td colspan="3" style="mso-ignore:colspan"/>
			</tr>
			<tr height="17" style="height:12.75pt">			
				<td height="17" colspan="2" style="height:12.75pt;mso-ignore:colspan"/>
				<td colspan="3" style="mso-ignore:colspan"/>
			</tr>
			<!-- PROJECT NAME -->
			<tr height="17" style="height:12.75pt">
				<td height="17" class="xl26" width="276" style="height:12.75pt;width:207pt">
					<xsl:value-of select="./Project/Name/@LabelTCL2"/>
				</td>
				<td class="xl27" width="234" style="width:176pt">
					<xsl:value-of select="./Project/Name"/>
				</td>
				<td colspan="4" style="mso-ignore:colspan"/>
			</tr>
			<!-- TEST OBJECT -->	
			<tr height="17" style="height:12.75pt">
				<td height="17" class="xl26" width="276" style="height:12.75pt;width:207pt">
					<xsl:value-of select="./TestObject/Name/@LabelTCL2"/>
				</td>
				<td class="xl27" width="234" style="width:176pt">
					<xsl:value-of select="./TestObject/Name"/>
				</td>
				<td colspan="4" style="mso-ignore:colspan"/>
			</tr>
			<!-- DATE -->
			<tr height="17" style="height:12.75pt">
				<td height="17" class="xl26" width="276" style="height:12.75pt;width:207pt">
					<xsl:value-of select="./Date/@LabelTCL2"/>
				</td>
				<td class="xl27" width="234" style="width:176pt">
					<xsl:value-of select="./Date"/>
				</td>
				<td colspan="4" style="mso-ignore:colspan"/>
			</tr>
			<!-- USER -->			
			<tr height="17" style="height:12.75pt">
			
				<td height="17" class="xl26" width="276" style="height:12.75pt;width:207pt">
					<xsl:value-of select="./UserName/@LabelTCL2"/>
				</td>
				<td class="xl27" width="234" style="width:176pt">
					<xsl:value-of select="./UserName"/>
				</td>
				<td colspan="4" style="mso-ignore:colspan"/>
			</tr>
			<!-- SPACE -->	
			<tr height="17" style="height:12.75pt">
				<td height="17" style="height:12.75pt"/>
				<div>
					<td colspan="5" style="mso-ignore:colspan"/>
				</div>
			</tr>
			<!-- GRID HEADERS -->	
			<tr height="17" style="mso-height-source:userset;height:12.75pt">
				<td height="17" class="xl28" style="height:12.75pt">
					<xsl:value-of select="./WorkSpace/Name/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/Name/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/Description/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/UserDescription/@LabelQA"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/State/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/RiskLevel/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/ReqNums/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="//tes:ReportTestCases/Element/Name/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="//tes:ReportTestCases/Element/EquivalenceClass/Description/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/CauseEffects/@LabelTCL2"/>
				</td>
				<td class="xl29">
					<xsl:value-of select="./TestCase/UserDescription/@LabelTCL2"/>
				</td>
	
			</tr>
		</table>
<!-- DATA TABLE -->			
		<table>
			<xsl:for-each select="./TestCase">
				<xsl:for-each select="./ElementReference">
					<xsl:variable name="EId" select="@Id"/>
					<xsl:if test="position()=1">
						<tr height="17" style="mso-height-source:userset;height:12.75pt">
						<!-- SUBJECT DATA -->	
							<td rowspan="3" height="51" class="xl31" width="276" style="border-bottom:.5pt solid black;height:38.25pt;border-top:none;width:207pt">
								<xsl:value-of select="/tes:ReportTestCases/WorkSpace/Name"/>
								<xsl:text>\</xsl:text>
								<xsl:value-of select="/tes:ReportTestCases/Project/Name"/>
								<xsl:text>\</xsl:text>
								<xsl:value-of select="/tes:ReportTestCases/TestObject/Name"/>
							</td>
						<!-- TESTNAME DATA -->								
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:variable name="testCaseName">
									<xsl:value-of select="../Name"/>
								</xsl:variable>
								<xsl:variable name="testCaseNameLenght">
									<xsl:value-of select="string-length($testCaseName)"/>
								</xsl:variable>
								<xsl:value-of select="substring($testCaseName,1,$testCaseNameLenght)"/>
							</td>
						<!-- DESCRIPTION -->			
							<td rowspan="3" class="xl31" width="320" style="border-bottom:.5pt solid black;border-top:none;width:240pt">
								<xsl:value-of select="../Description"/>
							</td>
							<!-- USER DESCRIPTION -->			
							<td rowspan="3" class="xl31" width="320" style="border-bottom:.5pt solid black;border-top:none;width:240pt">
								<xsl:value-of select="../UserDescription"/>
							</td>
							<!--STATE -->								
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:value-of select="../State"/>
							</td>
							<!--RISK LEVEL -->															
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:value-of select="../RiskLevel"/>
							</td>
							<!--REQUIREMENT -->														
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:for-each select="../ReqNums/tes:ReqNum">
									<xsl:value-of select="."/>
									<xsl:text>,</xsl:text>
								</xsl:for-each>
							</td>
							<!--STEP -->	
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:value-of select="//tes:ReportTestCases/Element/Name/@LabelTCL2"/><xsl:text> </xsl:text><xsl:value-of select="position()"/>
							</td>
							<!--ELEMENT : EQUIVALENCE CLASS -->							
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:value-of select="key(&apos;elementNameId&apos;,$EId)"/>
								<xsl:text>:</xsl:text>	
								<xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
							 		<xsl:value-of select="key(&apos;equivalenceClassValueId&apos;,$EqId)"/>	
								</xsl:for-each>
								
							</td>
							<!-- EFFECT OF EQUIVALENCE CLASS -->
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								   <xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
								   	<xsl:for-each select="key(&apos;equivalenceClassEffectId&apos;,$EqId)">
								   		<xsl:value-of select="./Description"/>,
								   	</xsl:for-each>
								   	</xsl:for-each>
							</td>
							<!-- EXPECTED RESULTS  : QUESTIONS -->							
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								   <xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
								   	<xsl:for-each select="key(&apos;equivalenceClassEffectId&apos;,$EqId)">
										 <xsl:for-each select="./ExpectedResult">
											 <xsl:value-of select="./Name"/>:<xsl:value-of select="./Value"/>,
								   		</xsl:for-each>
								   	</xsl:for-each>
								   	</xsl:for-each>
							</td>
						</tr>
						<tr height="17" style="mso-height-source:userset;height:12.75pt"/>
						<tr height="17" style="mso-height-source:userset;height:12.75pt"/>
					</xsl:if>
					<xsl:if test="position()&gt;1">
				
						<tr height="17" style="mso-height-source:userset;height:12.75pt">
							<td rowspan="3" height="51" class="xl31" width="276" style="border-bottom:.5pt solid black;height:38.25pt;border-top:none;width:207pt">
							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
							</td>
							<td rowspan="3" class="xl31" width="320" style="border-bottom:.5pt solid black;border-top:none;width:240pt">

							</td>
							<td rowspan="3" class="xl31" width="320" style="border-bottom:.5pt solid black;border-top:none;width:240pt">

							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">

							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">

							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:value-of select="//tes:ReportTestCases/Element/Name/@LabelTCL2"/><xsl:text> </xsl:text><xsl:value-of select="position()"/>
							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt"><xsl:value-of select="key(&apos;elementNameId&apos;,$EId)"/><xsl:text>:</xsl:text>	
								<xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
									<xsl:value-of select="key(&apos;equivalenceClassValueId&apos;,$EqId)"/>,	
								</xsl:for-each>
								</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								<xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
									<xsl:for-each select="key(&apos;equivalenceClassEffectId&apos;,$EqId)">
										<xsl:value-of select="./Description"/>,
									</xsl:for-each>
								</xsl:for-each>
							</td>
						<!-- EXPECTED RESULTS  : QUESTIONS -->							
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								   <xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
								   	<xsl:for-each select="key(&apos;equivalenceClassEffectId&apos;,$EqId)">
										 <xsl:for-each select="./ExpectedResult">
											 <xsl:value-of select="./Name"/>:<xsl:value-of select="./Value"/>,
								   		</xsl:for-each>
								   	</xsl:for-each>
								   	</xsl:for-each>
							</td>
						</tr>
						<tr height="17" style="mso-height-source:userset;height:12.75pt"/>
						<tr height="17" style="mso-height-source:userset;height:12.75pt"/>
					</xsl:if>
				</xsl:for-each>								
				<tr height="17" style="mso-height-source:userset;height:12.75pt">
							<td rowspan="3" height="51" class="xl31" width="276" style="border-bottom:.5pt solid black;height:38.25pt;border-top:none;width:207pt">
							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
							</td>
							<td rowspan="3" class="xl31" width="320" style="border-bottom:.5pt solid black;border-top:none;width:240pt">

							</td>
							<td rowspan="3" class="xl31" width="320" style="border-bottom:.5pt solid black;border-top:none;width:240pt">

							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">

							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">

							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
									END
							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
									<xsl:value-of select="./Description"/>
							</td>
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
   									<xsl:for-each select="./CauseEffects/CauseEffect/Description">
												<xsl:value-of select="."/>,
										</xsl:for-each>
							</td>
						<!-- EXPECTED RESULTS  : QUESTIONS OF TEST CASE-->							
							<td rowspan="3" class="xl31" width="234" style="border-bottom:.5pt solid black;border-top:none;width:176pt">
								   <xsl:for-each select="./CauseEffects/CauseEffect">
								   	 <xsl:for-each select="./ExpectedResult">
											 <xsl:value-of select="./Name"/>:<xsl:value-of select="./Value"/>,
								   	</xsl:for-each>
								   	</xsl:for-each>
							</td>
						</tr>
				<tr height="17" style="mso-height-source:userset;height:12.75pt"/>
				<tr height="17" style="mso-height-source:userset;height:12.75pt"/>

			</xsl:for-each>
		</table>
	</xsl:template>
</xsl:stylesheet>