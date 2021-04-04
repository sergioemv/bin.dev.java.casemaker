<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="casemaker$_$TestExpressions" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <rulesetParameters>
  <argument modifier="3" type="carrental.RentalAgreement" name="rental">
   <constant type="null" value="null"/>
  </argument>
  <argument modifier="3" type="carrental.Session" name="session">
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
 <package pkgName="complex">
  <packageVariables>
  </packageVariables>
  <rule name="Where">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;complex&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;complex.Where&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="carrental.CarGroup" bind="car_group">
     <naryOp name="and">
      <binaryOp name="in">
       <field name="Compact">
        <class className="carrental.CarGroup"/>
       </field>
       <method name="getCarGroups">
        <class className="carrental.CarGroup"/>
       </method>
      </binaryOp>
      <binaryOp name="!in">
       <field name="FullSize">
        <class className="carrental.CarGroup"/>
       </field>
       <method name="getCarGroups">
        <class className="carrental.CarGroup"/>
       </method>
      </binaryOp>
     </naryOp>
     <enumerator name="in">
      <method name="getCarGroups">
       <class className="carrental.CarGroup"/>
      </method>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="carrental.Branch" bind="branch">
     <unaryOp name="!">
      <field name="rejected">
       <variable name="rental"/>
      </field>
     </unaryOp>
     <enumerator name="from">
      <field name="pickupBranch">
       <variable name="rental"/>
      </field>
     </enumerator>
    </simpleCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="From">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;complex&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;complex.From&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="carrental.Customer" bind="customer">
     <enumerator name="from">
      <field name="customer">
       <variable name="rental"/>
      </field>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="carrental.Branch" bind="branch">
     <enumerator name="from">
      <field name="pickupBranch">
       <variable name="rental"/>
      </field>
     </enumerator>
    </simpleCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="In">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;complex&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;complex.In&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="carrental.CarGroup" bind="car_group">
     <enumerator name="in">
      <method name="getCarGroups">
       <class className="carrental.CarGroup"/>
      </method>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <simpleCondition asEvent="no" className="carrental.DateUtil" bind="date">
     <enumerator name="in">
      <field name="offers">
       <variable name="rental"/>
      </field>
     </enumerator>
    </simpleCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="translation.carrental.RentalAgreement">
  <packageVariables>
  </packageVariables>
  <function name="reject" type="void">
   <argument name="this" type="carrental.RentalAgreement"/>
   <block>
    <method name="setRejected">
     <variable name="this"/>
     <arguments>
      <constant type="boolean" value="true"/>
     </arguments>
    </method>
   </block>
  </function>
 </package>
 <package pkgName="forEach">
  <packageVariables>
  </packageVariables>
  <rule name="ForEach">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;forEach&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;forEach.ForEach&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="variable">
      <constant type="int" value="0"/>
     </bind>
    </evaluateCondition>
   </when>
   <then>
    <foreach>
     <foreachVariable className="carrental.Offer" name="ofertita"/>
     <foreachCollection>
      <field name="offers">
       <variable name="rental"/>
      </field>
     </foreachCollection>
     <block>
      <assign name="=">
       <field name="price">
        <variable name="ofertita"/>
       </field>
       <cast type="double">
        <constant type="int" value="45"/>
       </cast>
      </assign>
     </block>
    </foreach>
    <foreach>
     <foreachVariable className="carrental.CarGroup" name="var_0"/>
     <foreachCollection>
      <method name="getCarGroups">
       <class className="carrental.CarGroup"/>
      </method>
     </foreachCollection>
     <block>
      <method name="translation.carrental.RentalAgreement.reject">
       <arguments>
        <variable name="rental"/>
       </arguments>
      </method>
     </block>
    </foreach>
   </then>
  </rule>
 </package>
 <package pkgName="existence">
  <packageVariables>
  </packageVariables>
  <rule name="thereIsNo">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereIsNo&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <notCondition asEvent="no" className="carrental.Offer">
     <enumerator name="in">
      <field name="offers">
       <variable name="rental"/>
      </field>
     </enumerator>
    </notCondition>
    <evaluateCondition>
     <constant type="boolean" value="true"/>
    </evaluateCondition>
   </when>
   <then>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <constant type="string" value=" no offers found"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="thereAreAtMostOne">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereAreAtMostOne&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <collect bind="var_0">
     <element asEvent="no" className="carrental.Branch">
     </element>
    </collect>
    <collect bind="var_1">
     <element asEvent="no" className="carrental.Customer">
      <binaryOp name="==">
       <field name="birthYear">
       </field>
       <constant type="int" value="1996"/>
      </binaryOp>
     </element>
    </collect>
    <evaluateCondition>
     <naryOp name="and">
      <binaryOp name="le">
       <method name="size">
        <variable name="var_0"/>
       </method>
       <constant type="int" value="1"/>
      </binaryOp>
      <binaryOp name="le">
       <method name="size">
        <variable name="var_1"/>
       </method>
       <constant type="int" value="1"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value=" set price of standard offer for the"/>
       <method name="description">
        <variable name="rental"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="thereIsAtLeast">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereIsAtLeast&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <collect bind="var_0">
     <element asEvent="no" className="carrental.Branch">
      <enumerator name="in">
       <field name="offers">
        <variable name="rental"/>
       </field>
      </enumerator>
     </element>
    </collect>
    <evaluateCondition>
     <binaryOp name="gte">
      <method name="size">
       <variable name="var_0"/>
      </method>
      <constant type="int" value="48"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value=" set price of standard offer for the"/>
       <method name="description">
        <variable name="rental"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="thereAreAtLeastOne">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereAreAtLeastOne&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <exists asEvent="no" className="carrental.Branch">
    </exists>
    <exists asEvent="no" className="carrental.Customer">
     <binaryOp name="==">
      <field name="birthYear">
      </field>
      <constant type="int" value="1996"/>
     </binaryOp>
    </exists>
    <exists asEvent="no" className="carrental.CarGroup">
     <enumerator name="in">
      <method name="getCarGroups">
       <class className="carrental.CarGroup"/>
      </method>
     </enumerator>
    </exists>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <constant type="boolean" value="true"/>
       <constant type="boolean" value="true"/>
      </naryOp>
      <constant type="boolean" value="true"/>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value=" set price of standard offer for the"/>
       <method name="description">
        <variable name="rental"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="thereAreMorethan">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereAreMorethan&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <collect bind="var_0">
     <element asEvent="no" className="carrental.Offer">
     </element>
    </collect>
    <evaluateCondition>
     <binaryOp name="gt">
      <method name="size">
       <variable name="var_0"/>
      </method>
      <constant type="int" value="7"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value=" set price of standard offer for the"/>
       <method name="description">
        <variable name="rental"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="NumberOf">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.NumberOf&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <collect bind="var_0">
     <element asEvent="no" className="carrental.Customer">
     </element>
    </collect>
    <evaluateCondition>
     <binaryOp name="le">
      <method name="size">
       <variable name="var_0"/>
      </method>
      <constant type="int" value="5"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value=" set price of standard offer for the"/>
       <method name="description">
        <variable name="rental"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="thereIsAtMost">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereIsAtMost&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <collect bind="var_0">
     <element asEvent="no" className="carrental.CarGroup">
      <enumerator name="in">
       <method name="getCarGroups">
        <class className="carrental.CarGroup"/>
       </method>
      </enumerator>
     </element>
    </collect>
    <evaluateCondition>
     <binaryOp name="le">
      <method name="size">
       <variable name="var_0"/>
      </method>
      <constant type="int" value="8"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value=" set price of standard offer for the"/>
       <method name="description">
        <variable name="rental"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="thereIsOne">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereIsOne&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <collect bind="var_0">
     <element asEvent="no" className="carrental.Offer">
      <enumerator name="in">
       <field name="offers">
        <variable name="rental"/>
       </field>
      </enumerator>
     </element>
    </collect>
    <evaluateCondition>
     <binaryOp name="==">
      <method name="size">
       <variable name="var_0"/>
      </method>
      <constant type="int" value="1"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <constant type="string" value=" no offers found"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="thereAreLess">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;existence&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;existence.thereAreLess&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <collect bind="var_0">
     <element asEvent="no" className="carrental.Customer">
      <field name="loyaltyProgramMember">
      </field>
     </element>
    </collect>
    <evaluateCondition>
     <binaryOp name="lt">
      <method name="size">
       <variable name="var_0"/>
      </method>
      <constant type="int" value="2"/>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
    <method name="displayMessage">
     <variable name="session"/>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value=" set price of standard offer for the"/>
       <method name="description">
        <variable name="rental"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="definitions">
  <packageVariables>
  </packageVariables>
  <rule name="BynaryOp">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;definitions&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;definitions.BynaryOp&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="rental1">
      <binaryOp name="+">
       <constant type="string" value="The big"/>
       <field name="name">
        <field name="Compact">
         <class className="carrental.CarGroup"/>
        </field>
       </field>
      </binaryOp>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="carrental.Customer" bind="customer">
    </simpleCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="Object">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;definitions&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;definitions.Object&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement" bind="rental1">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="Age">
      <binaryOp name="+">
       <constant type="int" value="10"/>
       <method name="length">
        <constant type="string" value="This is really a long long long string"/>
       </method>
      </binaryOp>
     </bind>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="Array">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;definitions&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;definitions.Array&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="Better_Numbers">
      <newArray className="double">
       <realArray value="true"/>
       <dimension value="1"/>
       <initValues>
        <constant type="int" value="1"/>
        <constant type="int" value="4"/>
        <constant type="int" value="6"/>
       </initValues>
      </newArray>
     </bind>
    </evaluateCondition>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="float_values">
      <newArray className="double">
       <realArray value="true"/>
       <dimension value="1"/>
       <initValues>
        <field name="price">
         <field name="bestOffer">
          <variable name="rental"/>
         </field>
        </field>
        <field name="dailyRate">
         <field name="Compact">
          <class className="carrental.CarGroup"/>
         </field>
        </field>
       </initValues>
      </newArray>
     </bind>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="Collection">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;definitions&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;definitions.Collection&quot;"/>
    </property>
   </properties>
   <when>
    <collect bind="my_customers">
     <element asEvent="no" className="carrental.Customer">
     </element>
    </collect>
    <simpleCondition asEvent="no" className="carrental.RentalAgreement">
     <enumerator name="from">
      <variable name="rental"/>
     </enumerator>
    </simpleCondition>
    <collect bind="all_cogerages">
     <element asEvent="no" className="java.lang.String">
      <enumerator name="in">
       <field name="coverages">
        <variable name="rental"/>
       </field>
      </enumerator>
     </element>
    </collect>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
  <rule name="Constants">
   <priority>
    <field name="high">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;definitions&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;definitions.Constants&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="ilog.rules.engine.IlrContext">
     <enumerator name="from">
      <variable name="context"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <bind name="number">
      <constant type="int" value="50"/>
     </bind>
    </evaluateCondition>
    <evaluateCondition>
     <bind name="A_very_normal_String">
      <constant type="string" value="This is a textual value"/>
     </bind>
    </evaluateCondition>
   </when>
   <then>
    <method name="addOffer">
     <variable name="rental"/>
     <arguments>
      <constant type="string" value="standard"/>
     </arguments>
    </method>
    <method name="updateContext">
     <variable name="context"/>
    </method>
   </then>
  </rule>
 </package>
</ruleset>
