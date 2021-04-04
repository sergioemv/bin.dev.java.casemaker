package bi.view.testdataviews;
import model.ITypeData;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class CMUndoRedoTypeDatasInTestData {
    public CMUndoRedoTypeDatasInTestData(int p_indexTestData, int p_indexStructure, int p_IndexTypeData, ITypeData p_TypeData) {
        this.indexTestData=p_indexTestData;
        this.indexStructure= p_indexStructure;
        this.indexTypeData= p_IndexTypeData;
        this.m_TypeData= p_TypeData;
    }

    public ITypeData getM_TypeData(){ return m_TypeData; }

    public void setM_TypeData(ITypeData m_TypeData){ this.m_TypeData = m_TypeData; }

    public int getIndexTypeData(){
            return indexTypeData;
        }

    public void setIndexTypeData(int indexTypeData){
            this.indexTypeData = indexTypeData;
        }

    public int getIndexStructure(){
            return indexStructure;
        }

    public void setIndexStructure(int indexStructure){
            this.indexStructure = indexStructure;
        }

    public int getIndexTestData(){
            return indexTestData;
        }

    public void setIndexTestData(int indexTestData){
            this.indexTestData = indexTestData;
        }

    private ITypeData m_TypeData;
    private int indexTypeData;
    private int indexStructure;
    private int indexTestData;
}
