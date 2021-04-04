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

			</head>
			<body link="blue" vlink="purple">
				<xsl:apply-templates select="tes:ReportTestCases"/>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="tes:ReportTestCases">
		<table border="0" width="100%">
				<tbody>
					<tr>
						<td height="22">
							<h1 align="center">
								<span style="font-family:@Arial Unicode MS; font-style:oblique">List of Flow Test Cases</span>
							</h1>
						</td>
						<td align="center" height="22">
							<img border="0">
								<xsl:attribute name="src">images/cmlogoSmall.jpg</xsl:attribute>
							</img>
						</td>
					</tr>
				</tbody>
		</table>
		<hr color="#009933" size="2" />
		<table border="1" width="100%">
			<tbody>
				<tr>
					<th align="right">
							<xsl:for-each select="Project">
								<xsl:for-each select="Name">
									<xsl:for-each select="@LabelTCL2">
										<span style="font-family:@Arial Unicode MS; ">
											<xsl:value-of select="." />
										</span>
									</xsl:for-each>
								</xsl:for-each>
							</xsl:for-each>

					</th>
					<td style="background-color:silver; ">
							<xsl:for-each select="Project">
								<xsl:for-each select="Name">
									<span style="font-family:@Arial Unicode MS; ">
										<xsl:apply-templates />
									</span>
								</xsl:for-each>
							</xsl:for-each>

					</td>
				</tr>
				<tr>
					<th align="right">
							<xsl:for-each select="TestObject">
								<xsl:for-each select="Name">
									<xsl:for-each select="@LabelTCL2">
										<span style="font-family:@Arial Unicode MS; ">
											<xsl:value-of select="." />
										</span>
									</xsl:for-each>
								</xsl:for-each>
							</xsl:for-each>

					</th>
					<td style="background-color:silver; ">
							<xsl:for-each select="TestObject">
								<xsl:for-each select="Name">
									<span style="font-family:@Arial Unicode MS; ">
										<xsl:apply-templates />
									</span>
								</xsl:for-each>
							</xsl:for-each>
					</td>
				</tr>
				<tr>
					<th align="right">
							<xsl:for-each select="Date">
								<xsl:for-each select="@LabelTCL2">
									<span style="font-family:@Arial Unicode MS; ">
										<xsl:value-of select="." />
									</span>
								</xsl:for-each>
							</xsl:for-each>
					</th>
					<td style="background-color:silver; ">

							<xsl:for-each select="Date">
								<span style="font-family:@Arial Unicode MS; ">
									<xsl:apply-templates />
								</span>
							</xsl:for-each>
					</td>
				</tr>
				<tr>
					<th align="right">

							<xsl:for-each select="UserName">
								<xsl:for-each select="@LabelTCL2">
									<span style="font-family:@Arial Unicode MS; ">
										<xsl:value-of select="." />
									</span>
								</xsl:for-each>
							</xsl:for-each>
					</th>
					<td style="background-color:silver; ">
							<xsl:for-each select="UserName">
								<span style="font-family:@Arial Unicode MS; ">
									<xsl:apply-templates />
								</span>
							</xsl:for-each>

					</td>
				</tr>
			</tbody>
		</table>
		<hr color="#009933" size="2" />
		<object classid="clsid:279D6C9A-652E-4833-BEFC-312CA8887857" id="Viewer1" width="100%" height="70%"
			codebase="http://download.microsoft.com/download/visiostandard2003/vviewer/2003/w2kxp/en-us/vviewer.exe" >
			<param name="BackColor" value="16777200"/>
			<param name="AlertsEnabled" value="1"/>
			<param name="ContextMenuEnabled" value="1"/>
			<param name="GridVisible" value="0"/>
			<param name="HighQualityRender" value="1"/>
			<param name="PageColor" value="16777215"/>
			<param name="PageVisible" value="1"/>
			<param name="PropertyDialogEnabled" value="-1"/>
			<param name="ScrollbarsVisible" value="1"/>
			<param name="SizeGripVisible" value="1"/>
			<param name="ToolbarVisible" value="1"/>
			<xsl:for-each select="Origin">
			<param name="SRC">
				<xsl:attribute name="value"><xsl:apply-templates/></xsl:attribute>
				</param> 
					</xsl:for-each>
			<param name="CurrentPageIndex" value="0"/>
			<param name="Zoom" value="1"/>
		</object>
		<hr color="#009933" size="2" />
		<table border="1" width="100%" frame="border">
			<tr  style="font-family:@Arial Unicode MS;"  bgcolor="#66CC99" bordercolor="#009933" >
				<th >
					<xsl:value-of select="./WorkSpace/Name/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="./TestCase/Name/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="./TestCase/Description/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="./TestCase/State/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="./TestCase/RiskLevel/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="./TestCase/ReqNums/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="//tes:ReportTestCases/Element/Name/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="//tes:ReportTestCases/Element/EquivalenceClass/Description/@LabelTCL2"/>
				</th>
				<th>
					<xsl:value-of select="./TestCase/CauseEffects/@LabelTCL2"/>
				</th>
				
			</tr>

			<xsl:for-each select="./TestCase">
				<xsl:for-each select="./ElementReference">
					<xsl:variable name="EId" select="@Id"/>
					<xsl:if test="position()=1">
						
						<tr height="17"   style="font-family:@Arial Unicode MS;">
					<td  height="51"  width="276" >
								<xsl:value-of select="/tes:ReportTestCases/WorkSpace/Name"/>
								<xsl:text>\</xsl:text>
								<xsl:value-of select="/tes:ReportTestCases/Project/Name"/>
								<xsl:text>\</xsl:text>
								<xsl:value-of select="/tes:ReportTestCases/TestObject/Name"/>
							</td>
					<td   width="234" align="center">
								<xsl:variable name="testCaseName">
									<xsl:value-of select="../Name"/>
								</xsl:variable>
								<xsl:variable name="testCaseNameLenght">
									<xsl:value-of select="string-length($testCaseName)"/>
								</xsl:variable>
								<xsl:value-of select="substring($testCaseName,1,$testCaseNameLenght)"/>
							</td>
					<td  width="320" >
								<xsl:value-of select="../Description"/>
							</td>
					<td  width="234"  align="center">
								<xsl:value-of select="../State"/>
							</td>
					<td  width="234" align="center">
								<xsl:value-of select="../RiskLevel"/>
							</td>
					<td  width="234" >
								<xsl:for-each select="../ReqNums/tes:ReqNum">
									<xsl:value-of select="."/>
									<xsl:text>,</xsl:text>
								</xsl:for-each>
							</td>
									
				            <td   width="234" align="center">
								<xsl:value-of select="//tes:ReportTestCases/Element/Name/@LabelTCL2"/><xsl:text> </xsl:text><xsl:value-of select="position()"/>
							</td>
					<td   width="234" >
								<xsl:value-of select="key(&apos;elementNameId&apos;,$EId)"/>
								<xsl:text>:</xsl:text>	
								<xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
							 		<xsl:value-of select="key(&apos;equivalenceClassValueId&apos;,$EqId)"/>	
								</xsl:for-each>
								
							</td>
					<td   width="234" >
								   <xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
								   	<xsl:for-each select="key(&apos;equivalenceClassEffectId&apos;,$EqId)">
								   		<xsl:value-of select="./Description"/>,
								   	</xsl:for-each>
								   	</xsl:for-each>
							</td>
							
			</tr>

					</xsl:if>
					<xsl:if test="position()&gt;1">
						<tr height="17" style="font-family:@Arial Unicode MS;">
							<td  height="51"  width="276" />
							<td  width="234" />
							<td width="320" />
							<td width="234" />
							<td  width="234" />
							<td   width="234" />
							<td   width="234" align="center">
								<xsl:value-of select="//tes:ReportTestCases/Element/Name/@LabelTCL2"/><xsl:text> </xsl:text><xsl:value-of select="position()"/>
							</td>
							<td  width="234" >
								<xsl:value-of select="key(&apos;elementNameId&apos;,$EId)"/><xsl:text>:</xsl:text>	
								<xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
									<xsl:value-of select="key(&apos;equivalenceClassValueId&apos;,$EqId)"/>,	
								</xsl:for-each>
							</td>
							<td  width="234" >
								<xsl:for-each select="../EqClassReference [@ElementId=$EId]">
									<xsl:variable name="EqId" select="@UniqueId"/>
									<xsl:for-each select="key(&apos;equivalenceClassEffectId&apos;,$EqId)">
										<xsl:value-of select="./Description"/>,
									</xsl:for-each>
								</xsl:for-each>
							</td>

						</tr>
		
					</xsl:if>
				</xsl:for-each>								
				<tr height="17" style="font-family:@Arial Unicode MS;">
					<td  height="51"  width="276" />
					<td  width="234" />
					<td width="320" />
					<td width="234" />
					<td  width="234" />
					<td   width="234" />
					<td   width="234" align="center">
						END
					</td>
					<td  width="234" >
						<xsl:value-of select="./Description"/>
					</td>
					<td  width="234" >
   						<xsl:for-each select="./CauseEffects/CauseEffect/Description">
							<xsl:value-of select="."/>,
						</xsl:for-each>
					</td>

				</tr>
			</xsl:for-each>
		</table>
		<p>*  For better results use Internet Explorer</p>
	</xsl:template>
</xsl:stylesheet>