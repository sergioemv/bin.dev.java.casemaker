<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="loanvalidation$45$insurance" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <rulesetParameters>
  <argument modifier="3" type="loan.Borrower" name="insurance_borrower">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="7" type="loan.Loan" name="insurance_loan">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="5" type="loan.Report" name="insurance_report">
   <constant type="null" value="null"/>
  </argument>
 </rulesetParameters>
 <default-package pkgName="">
  <propertyDefinitions>
   <propertyType name="priority" className="java.lang.String"/>
   <propertyType name="effectiveDate" className="java.util.Date"/>
   <propertyType name="expirationDate" className="java.util.Date"/>
   <propertyType name="status" className="java.lang.String"/>
   <propertyType name="ilog.rules.package" className="ilog.rules.PackageHierarchy"/>
  </propertyDefinitions>
  <packageVariables>
  </packageVariables>
 </default-package>
 <package pkgName="insurance.test">
  <packageVariables>
  </packageVariables>
  <flowtask name="evaluation">
   <properties>
    <property name="mainflowtask">
     <constant type="string" value="false"/>
    </property>
   </properties>
   <finalActions>
    <block>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="insurance_borrower"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="insurance_loan"/>
      </arguments>
     </method>
     <method name="println">
      <field name="out">
       <variable name="context"/>
      </field>
      <arguments>
       <variable name="insurance_report"/>
      </arguments>
     </method>
    </block>
   </finalActions>
   <body>
    <taskInstance taskname="insurance.test.evaluation#insurance" name="null"/>
   </body>
  </flowtask>
  <ruletask name="evaluation#insurance">
   <initialActions>
    <block>
     <assign name="=">
      <variable name="insurance_report"/>
      <new className="loan.Report">
       <arguments>
        <variable name="insurance_borrower"/>
        <variable name="insurance_loan"/>
       </arguments>
      </new>
     </assign>
     <assign name="=">
      <field name="approved">
       <variable name="insurance_report"/>
      </field>
      <constant type="boolean" value="true"/>
     </assign>
    </block>
   </initialActions>
   <finalActions>
    <block>
    </block>
   </finalActions>
   <rulesFiring firing="1" ordering="0" firingLimit="0" algorithm="0"/>
   <dynamicBody value="false"/>
   <body>
    <rule name="insurance.defaultInsurance"/>
    <group groupName="insurance.insurance"/>
   </body>
  </ruletask>
 </package>
 <package pkgName="insurance">
  <packageVariables>
  </packageVariables>
  <rule name="insurance_0">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <constant type="int" value="100000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="false"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_1">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="100000"/>
         <constant type="int" value="300000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0010"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_2">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="300000"/>
         <constant type="int" value="600000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0030"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_3">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="A"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <constant type="int" value="600000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0050"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_4">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <constant type="int" value="100000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="false"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_5">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="100000"/>
         <constant type="int" value="300000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0025"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_6">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="300000"/>
         <constant type="int" value="600000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0050"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_7">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="B"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <constant type="int" value="600000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0075"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_8">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <constant type="int" value="100000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0035"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_9">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="100000"/>
         <constant type="int" value="300000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0060"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_10">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="in">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <interval>
         <leftLimit value="false"/>
         <rightLimit value="true"/>
         <constant type="int" value="300000"/>
         <constant type="int" value="600000"/>
        </interval>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0085"/>
    </assign>
   </then>
  </rule>
  <rule name="insurance_11">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.insurance&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="loan.Report">
     <enumerator name="from">
      <variable name="insurance_report"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="loan.Loan">
     <enumerator name="from">
      <variable name="insurance_loan"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <binaryOp name="!=">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <constant type="null" value="null"/>
       </binaryOp>
       <binaryOp name="gt">
        <method name="length">
         <field name="grade">
          <variable name="insurance_report"/>
         </field>
        </method>
        <constant type="int" value="0"/>
       </binaryOp>
      </naryOp>
      <naryOp name="and">
       <method name="equals">
        <field name="grade">
         <variable name="insurance_report"/>
        </field>
        <arguments>
         <constant type="string" value="C"/>
        </arguments>
       </method>
       <binaryOp name="gte">
        <field name="amount">
         <variable name="insurance_loan"/>
        </field>
        <constant type="int" value="600000"/>
       </binaryOp>
      </naryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.0145"/>
    </assign>
   </then>
  </rule>
  <rule name="defaultInsurance">
   <priority>
    <field name="low">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;insurance&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;insurance.defaultInsurance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <field name="insuranceRequired">
      <variable name="insurance_report"/>
     </field>
     <constant type="boolean" value="true"/>
    </assign>
    <assign name="=">
     <field name="insuranceRate">
      <variable name="insurance_report"/>
     </field>
     <constant type="double" value="0.02"/>
    </assign>
   </then>
  </rule>
 </package>
</ruleset>
