<?xml version="1.0" encoding="ISO-8859-1"?>
<ruleset name="eventmatching$45$rules" className="ilog.rules.engine.IlrContext" xmlns="http://www.ilog.com/products/xml/schemas/xrl_2.0">
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
 </default-package>
 <package pkgName="filter">
  <packageVariables>
  </packageVariables>
  <rule name="ShortAlarmToggle">
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
     <constant type="string" value="&quot;filter&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;filter.ShortAlarmToggle&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm1">
     <binaryOp name="!=">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm2">
     <binaryOp name="==">
      <field name="equipment">
      </field>
      <field name="equipment">
       <variable name="alarm1"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="id">
      </field>
      <field name="id">
       <variable name="alarm1"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
     <temporalOp name="after">
      <variable name="this"/>
      <variable name="alarm1"/>
      <constant type="int" value="0"/>
      <constant type="int" value="2"/>
     </temporalOp>
    </simpleCondition>
   </when>
   <then>
    <retract>
     <variable name="alarm1"/>
    </retract>
    <retract>
     <variable name="alarm2"/>
    </retract>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <binaryOp name="+">
         <binaryOp name="+">
          <constant type="string" value="Rule "/>
          <field name="ruleName">
           <variable name="instance"/>
          </field>
         </binaryOp>
         <constant type="string" value=" fired (at "/>
        </binaryOp>
        <method name="time">
         <variable name="context"/>
        </method>
       </binaryOp>
       <constant type="string" value=") on:"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarm1"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarm2"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="correlation">
  <packageVariables>
  </packageVariables>
  <rule name="RootCauseOnLink">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;correlation&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;correlation.RootCauseOnLink&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm1">
     <bind name="e1">
      <field name="equipment">
      </field>
     </bind>
     <binaryOp name="!=">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="probableCause">
      </field>
      <field name="Communications">
      </field>
     </binaryOp>
    </simpleCondition>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm2">
     <bind name="e2">
      <field name="equipment">
      </field>
     </bind>
     <binaryOp name="!=">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="probableCause">
      </field>
      <field name="Communications">
      </field>
     </binaryOp>
     <binaryOp name="!=">
      <variable name="e2"/>
      <variable name="e1"/>
     </binaryOp>
     <binaryOp name="gt">
      <field name="id">
      </field>
      <field name="id">
       <variable name="alarm1"/>
      </field>
     </binaryOp>
     <temporalOp name="before">
      <variable name="this"/>
      <variable name="alarm1"/>
      <constant type="int" value="-10"/>
      <constant type="int" value="10"/>
     </temporalOp>
    </simpleCondition>
    <simpleCondition asEvent="no" className="sample.afc.EquipmentLink" bind="link">
     <method name="isConnectedTo">
      <arguments>
       <variable name="e1"/>
      </arguments>
     </method>
     <method name="isConnectedTo">
      <arguments>
       <variable name="e2"/>
      </arguments>
     </method>
    </simpleCondition>
   </when>
   <then>
    <assert logical="no" asEvent="yes" withTimeExpression="no">
     <new className="sample.afc.Alarm">
      <arguments>
       <variable name="link"/>
       <method name="newCorrelatedAlarmId">
        <variable name="link"/>
       </method>
       <field name="Equipment">
        <class className="sample.afc.Alarm"/>
       </field>
       <field name="Major">
        <class className="sample.afc.Alarm"/>
       </field>
      </arguments>
     </new>
     <block>
     </block>
    </assert>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <binaryOp name="+">
         <binaryOp name="+">
          <constant type="string" value="Rule "/>
          <field name="ruleName">
           <variable name="instance"/>
          </field>
         </binaryOp>
         <constant type="string" value=" fired (at "/>
        </binaryOp>
        <method name="time">
         <variable name="context"/>
        </method>
       </binaryOp>
       <constant type="string" value=") on:"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarm1"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarm2"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="trace">
  <packageVariables>
  </packageVariables>
  <rule name="EquipmentInCriticalCondition$_$with$_$watchdog">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;trace&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;trace.EquipmentInCriticalCondition$_$with$_$watchdog&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.afc.AlarmState" bind="alarmState">
     <binaryOp name="==">
      <method name="perceivedSeverity">
      </method>
      <field name="Critical">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
    <wait logical="yes" until="no">
     <constant type="int" value="11"/>
     <conditions>
     </conditions>
    </wait>
   </when>
   <then>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <constant type="string" value="Rule "/>
        <field name="ruleName">
         <variable name="instance"/>
        </field>
       </binaryOp>
       <constant type="string" value=" fired after 10s:"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  equipment = "/>
       <field name="equipment">
        <variable name="alarmState"/>
       </field>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  time = "/>
       <method name="time">
        <variable name="context"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
    </method>
   </then>
  </rule>
  <rule name="EquipmentInCriticalCondition$_$with$_$events">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;trace&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;trace.EquipmentInCriticalCondition$_$with$_$events&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm">
     <binaryOp name="==">
      <field name="perceivedSeverity">
      </field>
      <field name="Critical">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
    <notCondition asEvent="yes" className="sample.afc.Alarm">
     <binaryOp name="!=">
      <field name="perceivedSeverity">
      </field>
      <field name="Critical">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="id">
      </field>
      <field name="id">
       <variable name="alarm"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="equipment">
      </field>
      <field name="equipment">
       <variable name="alarm"/>
      </field>
     </binaryOp>
     <temporalOp name="after">
      <variable name="this"/>
      <variable name="alarm"/>
      <constant type="int" value="0"/>
      <constant type="int" value="10"/>
     </temporalOp>
    </notCondition>
   </when>
   <then>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <constant type="string" value="Rule "/>
        <field name="ruleName">
         <variable name="instance"/>
        </field>
       </binaryOp>
       <constant type="string" value=" fired after 10s:"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  equipment = "/>
       <field name="equipment">
        <variable name="alarm"/>
       </field>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  time = "/>
       <method name="time">
        <variable name="context"/>
       </method>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
    </method>
   </then>
  </rule>
  <rule name="Equipment">
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
     <constant type="string" value="&quot;trace&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;trace.Equipment&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="no" className="sample.afc.Equipment" bind="equipment">
    </simpleCondition>
   </when>
   <then>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <variable name="equipment"/>
       <constant type="string" value=" declared."/>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
  <rule name="Alarm">
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
     <constant type="string" value="&quot;trace&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;trace.Alarm&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm">
    </simpleCondition>
   </when>
   <then>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <variable name="alarm"/>
        <constant type="string" value=" declared at "/>
       </binaryOp>
       <unaryOp name="timeof">
        <variable name="alarm"/>
       </unaryOp>
      </binaryOp>
     </arguments>
    </method>
   </then>
  </rule>
 </package>
 <package pkgName="alarm$_$state">
  <packageVariables>
  </packageVariables>
  <rule name="Aggregate">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;alarm$_$state&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;alarm$_$state.Aggregate&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm">
     <binaryOp name="!=">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
    <simpleCondition asEvent="no" className="sample.afc.AlarmState" bind="alarmState">
     <binaryOp name="==">
      <field name="equipment">
      </field>
      <field name="equipment">
       <variable name="alarm"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="id">
      </field>
      <field name="id">
       <variable name="alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
   </when>
   <then>
    <modify refresh="no">
     <variable name="alarmState"/>
     <method name="addAlarm">
      <arguments>
       <variable name="alarm"/>
      </arguments>
     </method>
    </modify>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <binaryOp name="+">
         <binaryOp name="+">
          <constant type="string" value="Rule "/>
          <field name="ruleName">
           <variable name="instance"/>
          </field>
         </binaryOp>
         <constant type="string" value=" fired (at "/>
        </binaryOp>
        <method name="time">
         <variable name="context"/>
        </method>
       </binaryOp>
       <constant type="string" value=") on:"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarm"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarmState"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
    </method>
   </then>
  </rule>
  <rule name="RemoveOnClearance">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;alarm$_$state&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;alarm$_$state.RemoveOnClearance&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm">
     <binaryOp name="==">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
    <simpleCondition asEvent="no" className="sample.afc.AlarmState" bind="alarmState">
     <binaryOp name="==">
      <field name="equipment">
      </field>
      <field name="equipment">
       <variable name="alarm"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="id">
      </field>
      <field name="id">
       <variable name="alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
   </when>
   <then>
    <retract>
     <variable name="alarmState"/>
    </retract>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <binaryOp name="+">
         <binaryOp name="+">
          <constant type="string" value="Rule "/>
          <field name="ruleName">
           <variable name="instance"/>
          </field>
         </binaryOp>
         <constant type="string" value=" fired (at "/>
        </binaryOp>
        <method name="time">
         <variable name="context"/>
        </method>
       </binaryOp>
       <constant type="string" value=") on:"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarm"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarmState"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
    </method>
   </then>
  </rule>
  <rule name="Creation">
   <properties>
    <property name="status">
     <constant type="string" value="&quot;new&quot;"/>
    </property>
    <property name="ilog.rules.package">
     <constant type="string" value="&quot;alarm$_$state&quot;"/>
    </property>
    <property name="ilog.rules.group">
     <constant type="string" value="&quot;alarm$_$state.Creation&quot;"/>
    </property>
   </properties>
   <when>
    <simpleCondition asEvent="yes" className="sample.afc.Alarm" bind="alarm">
     <binaryOp name="!=">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
    </simpleCondition>
    <notCondition asEvent="yes" className="sample.afc.Alarm">
     <binaryOp name="==">
      <field name="equipment">
      </field>
      <field name="equipment">
       <variable name="alarm"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="id">
      </field>
      <field name="id">
       <variable name="alarm"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="perceivedSeverity">
      </field>
      <field name="Cleared">
       <class className="sample.afc.Alarm"/>
      </field>
     </binaryOp>
     <temporalOp name="after">
      <variable name="this"/>
      <variable name="alarm"/>
      <constant type="int" value="0"/>
      <constant type="int" value="2"/>
     </temporalOp>
    </notCondition>
    <notCondition asEvent="no" className="sample.afc.AlarmState">
     <binaryOp name="==">
      <field name="equipment">
      </field>
      <field name="equipment">
       <variable name="alarm"/>
      </field>
     </binaryOp>
     <binaryOp name="==">
      <field name="id">
      </field>
      <field name="id">
       <variable name="alarm"/>
      </field>
     </binaryOp>
    </notCondition>
   </when>
   <then>
    <var name="alarmState" type="sample.afc.AlarmState">
     <new className="sample.afc.AlarmState">
      <arguments>
       <field name="equipment">
        <variable name="alarm"/>
       </field>
       <field name="id">
        <variable name="alarm"/>
       </field>
      </arguments>
     </new>
    </var>
    <method name="addAlarm">
     <variable name="alarmState"/>
     <arguments>
      <variable name="alarm"/>
     </arguments>
    </method>
    <assert logical="no" asEvent="no" withTimeExpression="no">
     <variable name="alarmState"/>
     <block>
     </block>
    </assert>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <binaryOp name="+">
        <binaryOp name="+">
         <binaryOp name="+">
          <constant type="string" value="Rule "/>
          <field name="ruleName">
           <variable name="instance"/>
          </field>
         </binaryOp>
         <constant type="string" value=" fired (at "/>
        </binaryOp>
        <method name="time">
         <variable name="context"/>
        </method>
       </binaryOp>
       <constant type="string" value=") on:"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
     <arguments>
      <binaryOp name="+">
       <constant type="string" value="  "/>
       <variable name="alarmState"/>
      </binaryOp>
     </arguments>
    </method>
    <method name="println">
     <field name="out">
      <variable name="context"/>
     </field>
    </method>
   </then>
  </rule>
 </package>
</ruleset>
