<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="ruleawareofcontext$45$rules" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <rulesetParameters>
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
  <function name="ilrmain" type="void">
   <argument name="arg" type="java.lang.Object"/>
   <block>
    <var name="cityDataProvider" type="sample.ruleawareofcontext.data.CityDataProvider">
     <new className="sample.ruleawareofcontext.data.CityDataProviderImpl">
     </new>
    </var>
    <var name="a" type="sample.ruleawareofcontext.model.Customer">
     <new className="sample.ruleawareofcontext.model.Customer">
      <arguments>
       <constant type="string" value="John Doe"/>
      </arguments>
     </new>
    </var>
    <assign name="=">
     <field name="age">
      <variable name="a"/>
     </field>
     <constant type="int" value="25"/>
    </assign>
    <assign name="=">
     <field name="billingAddress">
      <variable name="a"/>
     </field>
     <method name="getCityByName">
      <variable name="cityDataProvider"/>
      <arguments>
       <constant type="string" value="Auburn"/>
      </arguments>
     </method>
    </assign>
    <var name="b" type="sample.ruleawareofcontext.model.Customer">
     <new className="sample.ruleawareofcontext.model.Customer">
      <arguments>
       <constant type="string" value="Marc Green"/>
      </arguments>
     </new>
    </var>
    <assign name="=">
     <field name="age">
      <variable name="b"/>
     </field>
     <constant type="int" value="35"/>
    </assign>
    <assign name="=">
     <field name="billingAddress">
      <variable name="b"/>
     </field>
     <method name="getCityByName">
      <variable name="cityDataProvider"/>
      <arguments>
       <constant type="string" value="Barrow"/>
      </arguments>
     </method>
    </assign>
    <var name="c" type="sample.ruleawareofcontext.model.Customer">
     <new className="sample.ruleawareofcontext.model.Customer">
      <arguments>
       <constant type="string" value="Mike Cooper"/>
      </arguments>
     </new>
    </var>
    <assign name="=">
     <field name="age">
      <variable name="c"/>
     </field>
     <constant type="int" value="35"/>
    </assign>
    <assign name="=">
     <field name="salary">
      <variable name="c"/>
     </field>
     <constant type="int" value="40000"/>
    </assign>
    <assign name="=">
     <field name="billingAddress">
      <variable name="c"/>
     </field>
     <method name="getCityByName">
      <variable name="cityDataProvider"/>
      <arguments>
       <constant type="string" value="Miami"/>
      </arguments>
     </method>
    </assign>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="a"/>
     <block>
     </block>
    </assert>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="b"/>
     <block>
     </block>
    </assert>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="c"/>
     <block>
     </block>
    </assert>
    <method name="execute">
     <variable name="context"/>
    </method>
   </block>
  </function>
 </default-package>
 <package pkgName="F">
  <packageVariables>
  </packageVariables>
  <rule name="Incorrect_promotional_code_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;F&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;F.Incorrect_promotional_code_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Green Cove Springs"/>
       </arguments>
      </method>
      <binaryOp name="gte">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="25"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="S6"/>
    </assign>
   </then>
  </rule>
  <rule name="Miami_Bronze_Rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;F&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;F.Miami_Bronze_Rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <naryOp name="and">
       <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
        <arguments>
         <variable name="the_customer"/>
         <constant type="string" value="Miami"/>
        </arguments>
       </method>
       <binaryOp name="lt">
        <field name="age">
         <variable name="the_customer"/>
        </field>
        <constant type="int" value="45"/>
       </binaryOp>
      </naryOp>
      <binaryOp name="gte">
       <field name="salary">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="20000"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="Br"/>
    </assign>
   </then>
  </rule>
 </package>
 <package pkgName="No_state">
  <packageVariables>
  </packageVariables>
  <rule name="Albertville_Bronze_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;No_state&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;No_state.Albertville_Bronze_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Albertville"/>
       </arguments>
      </method>
      <binaryOp name="gte">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="25"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="Bz2"/>
    </assign>
   </then>
  </rule>
  <rule name="Barrow_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;No_state&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;No_state.Barrow_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Barrow"/>
       </arguments>
      </method>
      <binaryOp name="gt">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="40"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="D3"/>
    </assign>
   </then>
  </rule>
 </package>
 <package pkgName="translation.sample.ruleawareofcontext.model.Customer">
  <packageVariables>
  </packageVariables>
  <function name="isBillingAddress" type="boolean">
   <argument name="this" type="sample.ruleawareofcontext.model.Customer"/>
   <argument name="city" type="java.lang.String"/>
   <block>
    <if>
     <binaryOp name="!=">
      <method name="getBillingAddress">
       <variable name="this"/>
      </method>
      <constant type="null" value="null"/>
     </binaryOp>
     <ifthen>
      <return>
       <method name="equals">
        <field name="name">
         <method name="getBillingAddress">
          <variable name="this"/>
         </method>
        </field>
        <arguments>
         <variable name="city"/>
        </arguments>
       </method>
      </return>
     </ifthen>
     <ifelse>
      <return>
       <constant type="boolean" value="false"/>
      </return>
     </ifelse>
    </if>
   </block>
  </function>
 </package>
 <package pkgName="A1">
  <packageVariables>
  </packageVariables>
  <rule name="Auburn_Gold_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;A1&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;A1.Auburn_Gold_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Auburn"/>
       </arguments>
      </method>
      <binaryOp name="in">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="25"/>
        <constant type="int" value="40"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="G23"/>
    </assign>
   </then>
  </rule>
  <rule name="Albertville_Silver_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;A1&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;A1.Albertville_Silver_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Albertville"/>
       </arguments>
      </method>
      <binaryOp name="gte">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="25"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="DF5"/>
    </assign>
   </then>
  </rule>
 </package>
 <package pkgName="C1">
  <packageVariables>
  </packageVariables>
  <rule name="Bad_Promotional_code_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;C1&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;C1.Bad_Promotional_code_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Aspen"/>
       </arguments>
      </method>
      <binaryOp name="gte">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="25"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="BAD"/>
    </assign>
   </then>
  </rule>
  <rule name="Unknown_city_Gold_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;C1&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;C1.Unknown_city_Gold_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="UnknownCity"/>
       </arguments>
      </method>
      <binaryOp name="in">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <interval>
        <leftLimit value="false"/>
        <rightLimit value="false"/>
        <constant type="int" value="25"/>
        <constant type="int" value="40"/>
       </interval>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="G23"/>
    </assign>
   </then>
  </rule>
 </package>
 <package pkgName="A2">
  <packageVariables>
  </packageVariables>
  <rule name="Albertville_Silver_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;A2&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;A2.Albertville_Silver_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Albertville"/>
       </arguments>
      </method>
      <binaryOp name="gte">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="25"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="Fr5"/>
    </assign>
   </then>
  </rule>
  <rule name="Barrow_rule">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;A2&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;A2.Barrow_rule&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.ruleawareofcontext.model.Customer" bind="the_customer">
    </simpleCondition>
    <evaluateCondition>
     <naryOp name="and">
      <method name="translation.sample.ruleawareofcontext.model.Customer.isBillingAddress">
       <arguments>
        <variable name="the_customer"/>
        <constant type="string" value="Barrow"/>
       </arguments>
      </method>
      <binaryOp name="gte">
       <field name="age">
        <variable name="the_customer"/>
       </field>
       <constant type="int" value="25"/>
      </binaryOp>
     </naryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <field name="promoCode">
      <variable name="the_customer"/>
     </field>
     <constant type="string" value="DF5"/>
    </assign>
   </then>
  </rule>
 </package>
</ruleset>
