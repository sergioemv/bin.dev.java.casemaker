<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="bom2xom$45$rules" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
 <rulesetParameters>
  <argument modifier="3" type="samples.bom2xom.GenericObject" name="dispatcher">
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
   <argument modifier="1" type="samples.bom2xom.GenericObject" name="cashevent">
    <constant type="null" value="null"/>
   </argument>
   <argument modifier="1" type="samples.bom2xom.GenericObject" name="financialevent">
    <constant type="null" value="null"/>
   </argument>
  </packageVariables>
  <function name="ilrmain" type="void">
   <argument name="arg" type="java.lang.Object"/>
   <block>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <method name="translation.sample.financial.Account.create">
      <arguments>
       <constant type="string" value="AC1"/>
       <method name="getCurrency">
        <class className="sample.financial.util.Helper"/>
        <arguments>
         <constant type="string" value="EUR"/>
        </arguments>
       </method>
       <constant type="int" value="3400"/>
      </arguments>
     </method>
     <block>
     </block>
    </assert>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <method name="translation.sample.financial.Account.create">
      <arguments>
       <constant type="string" value="AC2"/>
       <method name="getCurrency">
        <class className="sample.financial.util.Helper"/>
        <arguments>
         <constant type="string" value="USD"/>
        </arguments>
       </method>
       <constant type="int" value="1350"/>
      </arguments>
     </method>
     <block>
     </block>
    </assert>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <method name="translation.sample.financial.Account.create">
      <arguments>
       <constant type="string" value="AC3"/>
       <method name="getCurrency">
        <class className="sample.financial.util.Helper"/>
        <arguments>
         <constant type="string" value="JPY"/>
        </arguments>
       </method>
       <constant type="int" value="768000"/>
      </arguments>
     </method>
     <block>
     </block>
    </assert>
    <var name="depositEvent" type="samples.bom2xom.GenericObject">
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.DEPOSIT">
       </method>
       <constant type="string" value="AC1"/>
       <constant type="null" value="null"/>
      </arguments>
     </method>
    </var>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="depositEvent"/>
      <constant type="int" value="200"/>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="depositEvent"/>
      <method name="getCurrency">
       <class className="sample.financial.util.Helper"/>
       <arguments>
        <constant type="string" value="EUR"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="depositEvent"/>
      <method name="getCurrentDate">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="depositEvent"/>
     <block>
     </block>
    </assert>
    <var name="transferEvent" type="samples.bom2xom.GenericObject">
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.TRANSFER">
       </method>
       <constant type="string" value="AC2"/>
       <constant type="null" value="null"/>
      </arguments>
     </method>
    </var>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="transferEvent"/>
      <constant type="int" value="6000"/>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="transferEvent"/>
      <method name="getCurrency">
       <class className="sample.financial.util.Helper"/>
       <arguments>
        <constant type="string" value="SGD"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.originAccountID">
     <arguments>
      <variable name="transferEvent"/>
      <constant type="string" value="AC3"/>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="transferEvent"/>
      <method name="getCurrentDate">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="transferEvent"/>
     <block>
     </block>
    </assert>
    <var name="ackEvent" type="samples.bom2xom.GenericObject">
     <method name="translation.sample.financial.FinancialEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.ACKNOWLEDGEMENT">
       </method>
       <constant type="string" value="AC3"/>
       <constant type="null" value="null"/>
      </arguments>
     </method>
    </var>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="ackEvent"/>
      <method name="getCurrentDate">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="ackEvent"/>
     <block>
     </block>
    </assert>
    <var name="purchaseEvent" type="samples.bom2xom.GenericObject">
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.CREDITCARD">
       </method>
       <constant type="string" value="AC1"/>
       <constant type="null" value="null"/>
      </arguments>
     </method>
    </var>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="purchaseEvent"/>
      <constant type="int" value="-120"/>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="purchaseEvent"/>
      <method name="getCurrency">
       <class className="sample.financial.util.Helper"/>
       <arguments>
        <constant type="string" value="EUR"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="purchaseEvent"/>
      <method name="getCurrentDate">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="purchaseEvent"/>
     <block>
     </block>
    </assert>
    <var name="debitEvent" type="samples.bom2xom.GenericObject">
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.DEBIT">
       </method>
       <constant type="string" value="AC1"/>
       <constant type="null" value="null"/>
      </arguments>
     </method>
    </var>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="debitEvent"/>
      <constant type="int" value="-120"/>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="debitEvent"/>
      <method name="getCurrency">
       <class className="sample.financial.util.Helper"/>
       <arguments>
        <constant type="string" value="EUR"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="debitEvent"/>
      <method name="getCurrentDate">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="debitEvent"/>
     <block>
     </block>
    </assert>
    <method name="execute">
     <variable name="context"/>
    </method>
   </block>
  </function>
 </default-package>
 <package pkgName="display">
  <packageVariables>
  </packageVariables>
  <rule name="AccountBalance">
   <priority>
    <field name="minimum">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;display&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;display.AccountBalance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="account">
     <method name="translation.sample.financial.Account._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
    </simpleCondition>
   </when>
   <then>
    <method name="translation.sample.financial.util.Helper.display">
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <binaryOp name="+">
         <constant type="string" value="Balance of account "/>
         <method name="translation.sample.financial.Account.ID">
          <arguments>
           <variable name="account"/>
          </arguments>
         </method>
        </binaryOp>
        <constant type="string" value=" = "/>
       </binaryOp>
       <method name="formattedBalance">
        <class className="sample.financial.util.Helper"/>
        <arguments>
         <method name="translation.sample.financial.Account.balance">
          <arguments>
           <variable name="account"/>
          </arguments>
         </method>
         <method name="translation.sample.financial.Account.currency">
          <arguments>
           <variable name="account"/>
          </arguments>
         </method>
        </arguments>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="EventDisplay">
   <priority>
    <field name="maximum">
     <class className="ilog.rules.engine.IlrPriorityValues"/>
    </field>
   </priority>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;display&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;display.EventDisplay&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="_event">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
    </simpleCondition>
   </when>
   <then>
    <method name="translation.sample.financial.util.Helper.display">
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="Start processing of a new event of "/>
       <method name="translation.sample.financial.FinancialEvent.description">
        <arguments>
         <variable name="_event"/>
        </arguments>
       </method>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="translation">
  <packageVariables>
  </packageVariables>
  <function name="checkParameterMap" type="void">
   <argument name="parameters" type="ilog.rules.engine.IlrParameterMap"/>
   <block>
    <method name="translation.sample.financial.EventDispatcher._cast">
     <arguments>
      <cast type="samples.bom2xom.GenericObject">
       <method name="getObjectValue">
        <variable name="parameters"/>
        <arguments>
         <constant type="string" value="dispatcher"/>
        </arguments>
       </method>
      </cast>
     </arguments>
    </method>
   </block>
  </function>
 </package>
 <package pkgName="translation.sample.financial.Account">
  <packageVariables>
  </packageVariables>
  <function name="create" type="samples.bom2xom.GenericObject">
   <argument name="ID" type="java.lang.String"/>
   <argument name="currency" type="sample.financial.util.Currency"/>
   <argument name="balance" type="double"/>
   <block>
    <var name="obj" type="samples.bom2xom.GenericObject">
     <method name="create">
      <class className="samples.bom2xom.GenericObjectHelper"/>
      <arguments>
       <constant type="string" value="Account"/>
      </arguments>
     </method>
    </var>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="ID"/>
      <variable name="ID"/>
     </arguments>
    </method>
    <method name="set">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="currency"/>
      <variable name="currency"/>
     </arguments>
    </method>
    <method name="setDouble">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="balance"/>
      <variable name="balance"/>
     </arguments>
    </method>
    <return>
     <variable name="obj"/>
    </return>
   </block>
  </function>
  <function name="_tester" type="boolean">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="isOfType">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="Account"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="ID" type="java.lang.String">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getString">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="ID"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="balance" type="double">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getDouble">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="balance"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="currency" type="sample.financial.util.Currency">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <cast type="sample.financial.util.Currency">
      <method name="get">
       <variable name="this"/>
       <arguments>
        <constant type="string" value="currency"/>
       </arguments>
      </method>
     </cast>
    </return>
   </block>
  </function>
  <function name="addBalance" type="void">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <argument name="ev" type="samples.bom2xom.GenericObject"/>
   <block>
    <var name="balance" type="double">
     <binaryOp name="+">
      <method name="getDouble">
       <variable name="this"/>
       <arguments>
        <constant type="string" value="balance"/>
       </arguments>
      </method>
      <method name="convert">
       <class className="sample.financial.util.Helper"/>
       <arguments>
        <method name="getDouble">
         <variable name="ev"/>
         <arguments>
          <constant type="string" value="amount"/>
         </arguments>
        </method>
        <cast type="sample.financial.util.Currency">
         <method name="get">
          <variable name="ev"/>
          <arguments>
           <constant type="string" value="currency"/>
          </arguments>
         </method>
        </cast>
        <cast type="sample.financial.util.Currency">
         <method name="get">
          <variable name="this"/>
          <arguments>
           <constant type="string" value="currency"/>
          </arguments>
         </method>
        </cast>
        <method name="getDate">
         <variable name="ev"/>
         <arguments>
          <constant type="string" value="date"/>
         </arguments>
        </method>
       </arguments>
      </method>
     </binaryOp>
    </var>
    <method name="setDouble">
     <variable name="this"/>
     <arguments>
      <constant type="string" value="balance"/>
      <variable name="balance"/>
     </arguments>
    </method>
   </block>
  </function>
 </package>
 <package pkgName="translation.sample.financial.CashEvent">
  <packageVariables>
  </packageVariables>
  <function name="create" type="samples.bom2xom.GenericObject">
   <argument name="arg1" type="java.lang.String"/>
   <argument name="arg2" type="java.lang.String"/>
   <argument name="arg3" type="java.lang.String"/>
   <block>
    <var name="obj" type="samples.bom2xom.GenericObject">
     <method name="create">
      <class className="samples.bom2xom.GenericObjectHelper"/>
      <arguments>
       <constant type="string" value="CashEvent"/>
      </arguments>
     </method>
    </var>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="ID"/>
      <method name="uniqueID">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="type"/>
      <variable name="arg1"/>
     </arguments>
    </method>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="accountID"/>
      <variable name="arg2"/>
     </arguments>
    </method>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="parentID"/>
      <variable name="arg3"/>
     </arguments>
    </method>
    <return>
     <variable name="obj"/>
    </return>
   </block>
  </function>
  <function name="amount" type="void">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <argument name="value" type="double"/>
   <block>
    <method name="setDouble">
     <variable name="this"/>
     <arguments>
      <constant type="string" value="amount"/>
      <variable name="value"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="currency" type="void">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <argument name="value" type="sample.financial.util.Currency"/>
   <block>
    <method name="set">
     <variable name="this"/>
     <arguments>
      <constant type="string" value="currency"/>
      <variable name="value"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="originAccountID" type="void">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <argument name="value" type="java.lang.String"/>
   <block>
    <method name="setString">
     <variable name="this"/>
     <arguments>
      <constant type="string" value="originAccountID"/>
      <variable name="value"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="_tester" type="boolean">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="isOfType">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="CashEvent"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="amount" type="double">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getDouble">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="amount"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="currency" type="sample.financial.util.Currency">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <cast type="sample.financial.util.Currency">
      <method name="get">
       <variable name="this"/>
       <arguments>
        <constant type="string" value="currency"/>
       </arguments>
      </method>
     </cast>
    </return>
   </block>
  </function>
  <function name="originAccountID" type="java.lang.String">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getString">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="originAccountID"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
 </package>
 <package pkgName="process.generic">
  <packageVariables>
  </packageVariables>
  <rule name="ProcessCascade">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.generic&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.generic.ProcessCascade&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="targetEvent">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
    </simpleCondition>
    <collect bind="subEvents">
     <element asEvent="no" className="samples.bom2xom.GenericObject">
      <method name="translation.sample.financial.FinancialEvent._tester">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <method name="equals">
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="targetEvent"/>
        </arguments>
       </method>
       <arguments>
        <method name="translation.sample.financial.FinancialEvent.parentID">
         <arguments>
          <variable name="this"/>
         </arguments>
        </method>
       </arguments>
      </method>
     </element>
     <where>
      <binaryOp name="gt">
       <method name="size">
        <variable name="this"/>
       </method>
       <constant type="int" value="0"/>
      </binaryOp>
     </where>
    </collect>
    <collect bind="processedEvents">
     <element asEvent="no" className="samples.bom2xom.GenericObject">
      <method name="translation.sample.financial.FinancialEvent._tester">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <method name="translation.sample.financial.FinancialEvent.processed">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <enumerator name="in">
       <variable name="subEvents"/>
      </enumerator>
     </element>
     <where>
      <binaryOp name="==">
       <method name="size">
        <variable name="this"/>
       </method>
       <method name="size">
        <variable name="subEvents"/>
       </method>
      </binaryOp>
     </where>
    </collect>
   </when>
   <then>
    <method name="translation.sample.financial.FinancialEvent.processedDate">
     <arguments>
      <variable name="targetEvent"/>
      <method name="getCurrentDate">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.processed">
     <arguments>
      <variable name="targetEvent"/>
      <constant type="boolean" value="true"/>
     </arguments>
    </method>
    <update refresh="no">
     <variable name="targetEvent"/>
    </update>
   </then>
  </rule>
  <rule name="ProcessAcknowledge">
   <comment>
 *   set the processed date of acknowledgeEvent to the current date ;
 </comment>
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.generic&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.generic.ProcessAcknowledge&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="acknowledgeEvent">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <constant type="string" value="ACKNOWLEDGEMENT"/>
      </arguments>
     </method>
    </simpleCondition>
   </when>
   <then>
    <method name="translation.sample.financial.FinancialEvent.processedDate">
     <arguments>
      <variable name="acknowledgeEvent"/>
      <method name="getCurrentDate">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.processed">
     <arguments>
      <variable name="acknowledgeEvent"/>
      <constant type="boolean" value="true"/>
     </arguments>
    </method>
    <update refresh="no">
     <variable name="acknowledgeEvent"/>
    </update>
   </then>
  </rule>
  <rule name="ConsumeEvent">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.generic&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.generic.ConsumeEvent&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="financialEvent">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="translation.sample.financial.FinancialEvent.processed">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
    </simpleCondition>
    <notCondition asEvent="no" className="samples.bom2xom.GenericObject">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.ID">
       <arguments>
        <variable name="financialEvent"/>
       </arguments>
      </method>
      <arguments>
       <method name="translation.sample.financial.FinancialEvent.parentID">
        <arguments>
         <variable name="this"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </notCondition>
    <evaluateCondition>
     <constant type="boolean" value="true"/>
    </evaluateCondition>
   </when>
   <then>
    <method name="translation.sample.financial.util.Helper.display">
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="Finish processing of event "/>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="financialEvent"/>
        </arguments>
       </method>
      </binaryOp>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.retract">
     <arguments>
      <variable name="financialEvent"/>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="process.eventtypes">
  <use>
   <objectUsed name="cashevent"/>
   <objectUsed name="financialevent"/>
  </use>
  <packageVariables>
  </packageVariables>
  <rule name="ProcessDepositEvent">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.eventtypes&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.eventtypes.ProcessDepositEvent&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="sourceEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <binaryOp name="in">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <newArray className="java.lang.Object">
       <realArray value="false"/>
       <dimension value="1"/>
       <initValues>
        <constant type="string" value="DEPOSIT"/>
        <constant type="string" value="REFUND"/>
       </initValues>
      </newArray>
     </binaryOp>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <variable name="cashevent" pkgName=""/>
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.CREDIT">
       </method>
       <method name="translation.sample.financial.FinancialEvent.accountID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.amount">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.currency">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="cashevent" pkgName=""/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="ProcessTransferEvent">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.eventtypes&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.eventtypes.ProcessTransferEvent&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="sourceEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <constant type="string" value="TRANSFER"/>
      </arguments>
     </method>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <variable name="cashevent" pkgName=""/>
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.CREDIT">
       </method>
       <method name="translation.sample.financial.FinancialEvent.accountID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.amount">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.currency">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="cashevent" pkgName=""/>
     </arguments>
    </method>
    <assign name="=">
     <variable name="cashevent" pkgName=""/>
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.DEBIT">
       </method>
       <method name="translation.sample.financial.CashEvent.originAccountID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <unaryOp name="-">
       <method name="translation.sample.financial.CashEvent.amount">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </unaryOp>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.currency">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="cashevent" pkgName=""/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="ProcessCreditCardEvent$_$CreateCashBack">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.eventtypes&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.eventtypes.ProcessCreditCardEvent$_$CreateCashBack&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="sourceEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <constant type="string" value="CREDITCARD"/>
      </arguments>
     </method>
    </simpleCondition>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="debitEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <binaryOp name="!=">
      <variable name="this"/>
      <variable name="sourceEvent"/>
     </binaryOp>
     <naryOp name="and">
      <method name="equals">
       <method name="translation.sample.financial.FinancialEvent.type">
        <arguments>
         <variable name="this"/>
        </arguments>
       </method>
       <arguments>
        <constant type="string" value="DEBIT"/>
       </arguments>
      </method>
      <method name="equals">
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <arguments>
        <method name="translation.sample.financial.FinancialEvent.parentID">
         <arguments>
          <variable name="this"/>
         </arguments>
        </method>
       </arguments>
      </method>
     </naryOp>
    </simpleCondition>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="account">
     <method name="translation.sample.financial.Account._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <naryOp name="and">
      <method name="equals">
       <method name="translation.sample.financial.Account.ID">
        <arguments>
         <variable name="this"/>
        </arguments>
       </method>
       <arguments>
        <method name="translation.sample.financial.FinancialEvent.accountID">
         <arguments>
          <variable name="debitEvent"/>
         </arguments>
        </method>
       </arguments>
      </method>
      <binaryOp name="gte">
       <method name="translation.sample.financial.Account.balance">
        <arguments>
         <variable name="this"/>
        </arguments>
       </method>
       <cast type="double">
        <constant type="int" value="0"/>
       </cast>
      </binaryOp>
     </naryOp>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <variable name="financialevent" pkgName=""/>
     <method name="translation.sample.financial.FinancialEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.CASHBACK">
       </method>
       <method name="translation.sample.financial.Account.ID">
        <arguments>
         <variable name="account"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="debitEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="financialevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="financialevent" pkgName=""/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="ProcessCreditDebitEvent">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.eventtypes&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.eventtypes.ProcessCreditDebitEvent&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="sourceEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <binaryOp name="in">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <newArray className="java.lang.Object">
       <realArray value="false"/>
       <dimension value="1"/>
       <initValues>
        <constant type="string" value="CREDIT"/>
        <constant type="string" value="DEBIT"/>
       </initValues>
      </newArray>
     </binaryOp>
    </simpleCondition>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="account">
     <method name="translation.sample.financial.Account._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.accountID">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
      <arguments>
       <method name="translation.sample.financial.Account.ID">
        <arguments>
         <variable name="this"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </simpleCondition>
   </when>
   <then>
    <method name="translation.sample.financial.Account.addBalance">
     <arguments>
      <variable name="account"/>
      <variable name="sourceEvent"/>
     </arguments>
    </method>
    <assign name="=">
     <variable name="financialevent" pkgName=""/>
     <method name="translation.sample.financial.FinancialEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.ACKNOWLEDGEMENT">
       </method>
       <method name="translation.sample.financial.Account.ID">
        <arguments>
         <variable name="account"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="financialevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.display">
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <binaryOp name="+">
         <constant type="string" value="Acknowledge processing of event "/>
         <method name="translation.sample.financial.FinancialEvent.ID">
          <arguments>
           <variable name="sourceEvent"/>
          </arguments>
         </method>
        </binaryOp>
        <constant type="string" value=" by event "/>
       </binaryOp>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="financialevent" pkgName=""/>
        </arguments>
       </method>
      </binaryOp>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="financialevent" pkgName=""/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="ProcessCashBackEvent">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.eventtypes&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.eventtypes.ProcessCashBackEvent&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="cashbackEvent">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <constant type="string" value="CASHBACK"/>
      </arguments>
     </method>
    </simpleCondition>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="sourceEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <binaryOp name="!=">
      <variable name="this"/>
      <variable name="cashbackEvent"/>
     </binaryOp>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.ID">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <method name="translation.sample.financial.FinancialEvent.parentID">
        <arguments>
         <variable name="cashbackEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <variable name="cashevent" pkgName=""/>
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.REFUND">
       </method>
       <method name="translation.sample.financial.FinancialEvent.accountID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="cashbackEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <binaryOp name="*">
       <unaryOp name="-">
        <method name="translation.sample.financial.CashEvent.amount">
         <arguments>
          <variable name="sourceEvent"/>
         </arguments>
        </method>
       </unaryOp>
       <constant type="double" value="0.05"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.currency">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="cashbackEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="cashevent" pkgName=""/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="ProcessCreditCardEvent">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.eventtypes&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.eventtypes.ProcessCreditCardEvent&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="sourceEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <constant type="string" value="CREDITCARD"/>
      </arguments>
     </method>
    </simpleCondition>
    <evaluateCondition>
     <binaryOp name="lt">
      <method name="translation.sample.financial.CashEvent.amount">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
      <cast type="double">
       <constant type="int" value="0"/>
      </cast>
     </binaryOp>
    </evaluateCondition>
   </when>
   <then>
    <assign name="=">
     <variable name="cashevent" pkgName=""/>
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.DEBIT">
       </method>
       <method name="translation.sample.financial.FinancialEvent.accountID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.amount">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.currency">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="cashevent" pkgName=""/>
     </arguments>
    </method>
   </then>
   <else>
    <assign name="=">
     <variable name="cashevent" pkgName=""/>
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.REFUND">
       </method>
       <method name="translation.sample.financial.FinancialEvent.accountID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.amount">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.currency">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="cashevent" pkgName=""/>
     </arguments>
    </method>
   </else>
  </rule>
  <rule name="ProcessWithdrawEvent">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;process.eventtypes&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;process.eventtypes.ProcessWithdrawEvent&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="sourceEvent">
     <method name="translation.sample.financial.CashEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <binaryOp name="in">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <newArray className="java.lang.Object">
       <realArray value="false"/>
       <dimension value="1"/>
       <initValues>
        <constant type="string" value="WITHDRAWAL"/>
       </initValues>
      </newArray>
     </binaryOp>
    </simpleCondition>
   </when>
   <then>
    <assign name="=">
     <variable name="cashevent" pkgName=""/>
     <method name="translation.sample.financial.CashEvent.create">
      <arguments>
       <method name="translation.sample.financial.EventType.DEBIT">
       </method>
       <method name="translation.sample.financial.FinancialEvent.accountID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
       <method name="translation.sample.financial.FinancialEvent.ID">
        <arguments>
         <variable name="sourceEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </assign>
    <method name="translation.sample.financial.CashEvent.amount">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.amount">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.CashEvent.currency">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.CashEvent.currency">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.FinancialEvent.date">
     <arguments>
      <variable name="cashevent" pkgName=""/>
      <method name="translation.sample.financial.FinancialEvent.date">
       <arguments>
        <variable name="sourceEvent"/>
       </arguments>
      </method>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.insert">
     <arguments>
      <variable name="cashevent" pkgName=""/>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="errors">
  <packageVariables>
  </packageVariables>
  <rule name="AcknowledgeWithoutCauseError">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;errors&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;errors.AcknowledgeWithoutCauseError&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="targetEvent">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.type">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <constant type="string" value="ACKNOWLEDGEMENT"/>
      </arguments>
     </method>
    </simpleCondition>
    <notCondition asEvent="no" className="samples.bom2xom.GenericObject">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <method name="equals">
      <method name="translation.sample.financial.FinancialEvent.ID">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
      <arguments>
       <method name="translation.sample.financial.FinancialEvent.parentID">
        <arguments>
         <variable name="targetEvent"/>
        </arguments>
       </method>
      </arguments>
     </method>
    </notCondition>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="var_0">
     <method name="translation.sample.financial.EventDispatcher._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <enumerator name="from">
      <variable name="dispatcher"/>
     </enumerator>
    </simpleCondition>
    <evaluateCondition>
     <constant type="boolean" value="true"/>
    </evaluateCondition>
   </when>
   <then>
    <method name="exception">
     <class className="samples.bom2xom.extenders.DispatcherExtender"/>
     <arguments>
      <variable name="var_0"/>
      <variable name="targetEvent"/>
      <constant type="string" value="has no parent event"/>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.retract">
     <arguments>
      <variable name="targetEvent"/>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="UnprocessedEventsError">
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
     <constant type="string" value="&quot;errors&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;errors.UnprocessedEventsError&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="unprocessedEvent">
     <method name="translation.sample.financial.FinancialEvent._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <unaryOp name="!">
      <method name="translation.sample.financial.FinancialEvent.processed">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
     </unaryOp>
    </simpleCondition>
    <simpleCondition asEvent="no" className="samples.bom2xom.GenericObject" bind="var_0">
     <method name="translation.sample.financial.EventDispatcher._tester">
      <arguments>
       <variable name="this"/>
      </arguments>
     </method>
     <enumerator name="from">
      <variable name="dispatcher"/>
     </enumerator>
    </simpleCondition>
   </when>
   <then>
    <method name="exception">
     <class className="samples.bom2xom.extenders.DispatcherExtender"/>
     <arguments>
      <variable name="var_0"/>
      <variable name="unprocessedEvent"/>
      <constant type="string" value="is unprocessed"/>
     </arguments>
    </method>
    <method name="translation.sample.financial.util.Helper.retract">
     <arguments>
      <variable name="unprocessedEvent"/>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="translation.sample.financial.util.Helper">
  <packageVariables>
  </packageVariables>
  <function name="display" type="void">
   <argument name="text" type="java.lang.String"/>
   <block>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <variable name="text"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="retract" type="void">
   <argument name="obj" type="java.lang.Object"/>
   <block>
    <retract>
     <variable name="obj"/>
    </retract>
   </block>
  </function>
  <function name="insert" type="void">
   <argument name="obj" type="java.lang.Object"/>
   <block>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="obj"/>
     <block>
     </block>
    </assert>
   </block>
  </function>
 </package>
 <package pkgName="process">
  <packageVariables>
  </packageVariables>
 </package>
 <package pkgName="translation.sample.financial.FinancialEvent">
  <packageVariables>
  </packageVariables>
  <function name="date" type="void">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <argument name="value" type="java.util.Date"/>
   <block>
    <method name="setDate">
     <variable name="this"/>
     <arguments>
      <constant type="string" value="date"/>
      <variable name="value"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="create" type="samples.bom2xom.GenericObject">
   <argument name="arg1" type="java.lang.String"/>
   <argument name="arg2" type="java.lang.String"/>
   <argument name="arg3" type="java.lang.String"/>
   <block>
    <var name="obj" type="samples.bom2xom.GenericObject">
     <method name="create">
      <class className="samples.bom2xom.GenericObjectHelper"/>
      <arguments>
       <constant type="string" value="FinancialEvent"/>
      </arguments>
     </method>
    </var>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="ID"/>
      <method name="uniqueID">
       <class className="sample.financial.util.Helper"/>
      </method>
     </arguments>
    </method>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="type"/>
      <variable name="arg1"/>
     </arguments>
    </method>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="accountID"/>
      <variable name="arg2"/>
     </arguments>
    </method>
    <method name="setString">
     <variable name="obj"/>
     <arguments>
      <constant type="string" value="parentID"/>
      <variable name="arg3"/>
     </arguments>
    </method>
    <return>
     <variable name="obj"/>
    </return>
   </block>
  </function>
  <function name="_tester" type="boolean">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="isOfType">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="FinancialEvent"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="description" type="java.lang.String">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="toString">
      <variable name="this"/>
     </method>
    </return>
   </block>
  </function>
  <function name="type" type="java.lang.String">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getString">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="type"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="ID" type="java.lang.String">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getString">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="ID"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="parentID" type="java.lang.String">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getString">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="parentID"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="processed" type="boolean">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getBoolean">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="processed"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="processedDate" type="void">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <argument name="value" type="java.util.Date"/>
   <block>
    <method name="setDate">
     <variable name="this"/>
     <arguments>
      <constant type="string" value="processedDate"/>
      <variable name="value"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="processed" type="void">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <argument name="value" type="boolean"/>
   <block>
    <method name="setBoolean">
     <variable name="this"/>
     <arguments>
      <constant type="string" value="processed"/>
      <constant type="boolean" value="true"/>
     </arguments>
    </method>
   </block>
  </function>
  <function name="accountID" type="java.lang.String">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getString">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="accountID"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="date" type="java.util.Date">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="getDate">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="date"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
 </package>
 <package pkgName="translation.sample.financial.EventDispatcher">
  <packageVariables>
  </packageVariables>
  <function name="_tester" type="boolean">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <return>
     <method name="isOfType">
      <variable name="this"/>
      <arguments>
       <constant type="string" value="EventDispatcher"/>
      </arguments>
     </method>
    </return>
   </block>
  </function>
  <function name="_cast" type="samples.bom2xom.GenericObject">
   <argument name="this" type="samples.bom2xom.GenericObject"/>
   <block>
    <if>
     <naryOp name="or">
      <binaryOp name="==">
       <variable name="this"/>
       <constant type="null" value="null"/>
      </binaryOp>
      <method name="translation.sample.financial.EventDispatcher._tester">
       <arguments>
        <variable name="this"/>
       </arguments>
      </method>
     </naryOp>
     <ifthen>
      <return>
       <variable name="this"/>
      </return>
     </ifthen>
    </if>
    <throw>
     <new className="java.lang.ClassCastException">
      <arguments>
       <method name="format">
        <class className="ilog.rules.util.prefs.IlrMessages"/>
        <arguments>
         <constant type="string" value="translation.BusinessClassCastException"/>
         <method name="getName">
          <method name="getClass">
           <variable name="this"/>
          </method>
         </method>
         <constant type="string" value="sample.financial.EventDispatcher"/>
        </arguments>
       </method>
      </arguments>
     </new>
    </throw>
   </block>
  </function>
 </package>
 <package pkgName="translation.sample.financial.EventType">
  <packageVariables>
  </packageVariables>
  <function name="DEPOSIT" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="DEPOSIT"/>
    </return>
   </block>
  </function>
  <function name="TRANSFER" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="TRANSFER"/>
    </return>
   </block>
  </function>
  <function name="ACKNOWLEDGEMENT" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="ACKNOWLEDGEMENT"/>
    </return>
   </block>
  </function>
  <function name="CREDITCARD" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="CREDITCARD"/>
    </return>
   </block>
  </function>
  <function name="DEBIT" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="DEBIT"/>
    </return>
   </block>
  </function>
  <function name="CREDIT" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="CREDIT"/>
    </return>
   </block>
  </function>
  <function name="CASHBACK" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="CASHBACK"/>
    </return>
   </block>
  </function>
  <function name="REFUND" type="java.lang.String">
   <block>
    <return>
     <constant type="string" value="REFUND"/>
    </return>
   </block>
  </function>
 </package>
</ruleset>
