<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:a="http://schemas.microsoft.com/visio/2003/core" exclude-result-prefixes="a">
	<xsl:output method="text" indent="no" encoding="iso-8859-1"/>
	<!-- Variable declaration -->
	<xsl:variable name="InitialState">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='Initial State'">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:variable name="FinalState">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='Final State'">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:variable name="State">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='State'">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:variable name="ControlFlow">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='Control Flow'">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:variable name="Transition">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='Transition '">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:variable name="ObjectFlow">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='Object Flow'">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:variable name="Transition2">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='Transition'">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<xsl:variable name="Constraint">
		<xsl:for-each select="a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@NameU='Constraint'">
				<xsl:value-of select="@ID"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:variable>
	<!-- Main select -->
	<xsl:template match="/">
		<xsl:for-each select="a:VisioDocument/a:Pages/a:Page">
		Begin_Page;
		Page_Name:"<xsl:value-of select="@Name"/>"; 
			<xsl:for-each select="a:Shapes/a:Shape">
				<xsl:call-template name="selectInitialState"/>
				<xsl:call-template name="selectState"/>
				<xsl:call-template name="selectConstraint"/>
				<xsl:call-template name="selectRelation"/>
				<xsl:call-template name="selectTransition"/>
				<xsl:call-template name="selectFinalState"/>
			</xsl:for-each>End_Page;</xsl:for-each>
	</xsl:template>
	<xsl:template name="selectInitialState">
		<xsl:choose>
			<xsl:when test="@Master=$InitialState"> 
			Begin_InitialState;
			Type="Initial State";
			ID=<xsl:value-of select="@ID"/>;
			End_InitialState;
		</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="Flag">
					<xsl:call-template name="alternativeInitialStateMatch"/>
				</xsl:variable>
				<xsl:if test="$Flag='true'">
				Begin_InitialState;
				Type="Initial State";
				ID=<xsl:value-of select="@ID"/>;
				End_InitialState;
			</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="selectFinalState">
		<xsl:choose>
			<xsl:when test="@Master=$FinalState">
			Begin_FinalState;
			Type="Final State";
			ID=<xsl:value-of select="@ID"/>;
			End_FinalState;
		</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="Flag">
					<xsl:call-template name="alternativeFinalStateMatch"/>
				</xsl:variable>
				<xsl:if test="$Flag='true'">
				Begin_FinalState;
				Type="Final State";
				ID=<xsl:value-of select="@ID"/>;
				End_FinalState;
			</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="selectState">
		<xsl:choose>
			<xsl:when test="@Master=$State"> 
	Begin_State;
		<xsl:call-template name="selectTextStates"/>
	End_State;
	</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="Flag">
					<xsl:call-template name="alternativeStateMatch"/>
				</xsl:variable>
				<xsl:if test="$Flag='true'">
			Begin_State;
				<xsl:call-template name="selectTextStates"/>
			End_State;
		</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="selectConstraint">
		<xsl:choose>
			<xsl:when test="@Master=$Constraint"> 
	Begin_Constraint;
		<xsl:call-template name="selectTextConstraint"/>
	End_Constraint;
	</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="Flag">
					<xsl:call-template name="alternativeConstraintMatch"/>
				</xsl:variable>
				<xsl:if test="$Flag='true'">
			Begin_Constraint;
				<xsl:call-template name="selectTextConstraint"/>
			End_Constraint;
		</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="selectTransition">
		<xsl:choose>
			<xsl:when test="@Master=$ControlFlow or @Master=$Transition or @Master=$Transition2">
		Begin_Transition;
			<xsl:call-template name="selectTextTransition"/>
				<xsl:call-template name="selectPahs"/>
		End_Transition;
		</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="Flag">
					<xsl:call-template name="alternativeTransitionMatch"/>
				</xsl:variable>
				<xsl:if test="$Flag='true'">
				Begin_Transition;
				<xsl:call-template name="selectTextTransition"/>
					<xsl:call-template name="selectPahs"/>
				End_Transition;
			</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="selectRelation">
		<xsl:choose>
			<xsl:when test="@Master=$ObjectFlow">
		Begin_Relation;
				<xsl:call-template name="selectPahs"/>
		End_Relation;
		</xsl:when>
			<xsl:otherwise>
				<xsl:variable name="Flag">
					<xsl:call-template name="alternativeRelationMatch"/>
				</xsl:variable>
				<xsl:if test="$Flag='true'">
				Begin_Relation;
					<xsl:call-template name="selectPahs"/>
				End_Relation;
			</xsl:if>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="selectTextStates">
		Type="State";
		ID=<xsl:value-of select="@ID"/>;
	<xsl:for-each select="a:Shapes/a:Shape">
			<xsl:if test="@Name='Name'">
			Business Action="<xsl:value-of select="a:Text"/>";
		</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="selectTextConstraint">
		ID=<xsl:value-of select="@ID"/>;
	<xsl:for-each select="a:Shapes/a:Shape">
			<xsl:if test="@Name='Name'">
				<xsl:value-of select="substring(a:Text,2,string-length(a:Text)-3)"/>
				
					</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="selectTextTransition">
			Transition="<xsl:value-of select="a:Text"/>";
</xsl:template>
	<xsl:template name="selectPahs">
		<xsl:variable name="BeginX">
			<xsl:call-template name="getBeginX"/>
		</xsl:variable>
		<xsl:variable name="EndX">
			<xsl:call-template name="getEndX"/>
		</xsl:variable>
	Begin=".<xsl:value-of select="$BeginX"/>";
	<!---><xsl:value-of select="substring-before(substring-after(a:XForm1D/a:BeginX/@F,'PNT('),'!')"/>";-->
	End=".<xsl:value-of select="$EndX"/>";
	</xsl:template>
	<xsl:template name="alternativeStateMatch">
		<xsl:variable name="MasterID">
			<xsl:value-of select="@Master"/>
		</xsl:variable>
		<xsl:for-each select="/a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@ID=$MasterID">
				<xsl:choose>
					<xsl:when test="starts-with(@NameU,'State')">
						<xsl:value-of select="true()"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="false()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="alternativeConstraintMatch">
		<xsl:variable name="MasterID">
			<xsl:value-of select="@Master"/>
		</xsl:variable>
		<xsl:for-each select="/a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@ID=$MasterID">
				<xsl:choose>
					<xsl:when test="starts-with(@NameU,'Constraint')">
						<xsl:value-of select="true()"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="false()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="alternativeInitialStateMatch">
		<xsl:variable name="MasterID">
			<xsl:value-of select="@Master"/>
		</xsl:variable>
		<xsl:for-each select="/a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@ID=$MasterID">
				<xsl:choose>
					<xsl:when test="starts-with(@NameU,'Initial State')">
						<xsl:value-of select="true()"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="false()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="alternativeFinalStateMatch">
		<xsl:variable name="MasterID">
			<xsl:value-of select="@Master"/>
		</xsl:variable>
		<xsl:for-each select="/a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@ID=$MasterID">
				<xsl:choose>
					<xsl:when test="starts-with(@NameU,'Final State')">
						<xsl:value-of select="true()"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="false()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="alternativeTransitionMatch">
		<xsl:variable name="MasterID">
			<xsl:value-of select="@Master"/>
		</xsl:variable>
		<xsl:for-each select="/a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@ID=$MasterID">
				<xsl:choose>
					<xsl:when test="starts-with(@NameU,'Control Flow') or starts-with(@NameU,'Transition ') or starts-with(@NameU,'Transition')">
						<xsl:value-of select="true()"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="false()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="alternativeRelationMatch">
		<xsl:variable name="MasterID">
			<xsl:value-of select="@Master"/>
		</xsl:variable>
		<xsl:for-each select="/a:VisioDocument/a:Masters/a:Master">
			<xsl:if test="@ID=$MasterID">
				<xsl:choose>
					<xsl:when test="starts-with(@NameU,'Object Flow')">
						<xsl:value-of select="true()"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="false()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="getBeginX">
		<xsl:variable name="TransitionID">
			<xsl:value-of select="@ID"/>
		</xsl:variable>
		<xsl:for-each select="../../a:Connects/a:Connect">
			<xsl:if test=" $TransitionID =@FromSheet and @FromCell='BeginX'">
				<xsl:value-of select="@ToSheet"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="getEndX">
		<xsl:variable name="TransitionID">
			<xsl:value-of select="@ID"/>
		</xsl:variable>
		<xsl:for-each select="../../a:Connects/a:Connect">
			<xsl:if test="$TransitionID=@FromSheet and @FromCell='EndX'">
				<xsl:value-of select="@ToSheet"/>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
