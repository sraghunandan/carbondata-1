/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.carbondata.core.scan.executor.infos;

import java.util.Map;

import org.apache.carbondata.core.datastore.DataRefNode;
import org.apache.carbondata.core.datastore.IndexKey;
import org.apache.carbondata.core.datastore.block.AbstractIndex;
import org.apache.carbondata.core.keygenerator.KeyGenerator;
import org.apache.carbondata.core.metadata.AbsoluteTableIdentifier;
import org.apache.carbondata.core.mutate.DeleteDeltaVo;
import org.apache.carbondata.core.scan.filter.GenericQueryType;
import org.apache.carbondata.core.scan.filter.executer.FilterExecuter;
import org.apache.carbondata.core.scan.model.QueryDimension;
import org.apache.carbondata.core.scan.model.QueryMeasure;

/**
 * Below class will have all the properties which needed during query execution
 * for one block
 */
public class BlockExecutionInfo {

  /**
   * block on which query will be executed
   */
  private AbstractIndex blockIndex;

  /**
   * below to store all the information required for measures during query
   * execution
   */
  private MeasureInfo measureInfo;

  /**
   * this will be used to get the first tentative block from which query
   * execution start, this will be useful in case of filter query to get the
   * start block based on filter values
   */
  private IndexKey startKey;

  /**
   * this will be used to get the last tentative block till which scanning
   * will be done, this will be useful in case of filter query to get the last
   * block based on filter values
   */
  private IndexKey endKey;

  private String blockId;

  /**
   * total number of dimension in block
   */
  private int totalNumberDimensionBlock;

  /**
   * total number of measure in block
   */
  private int totalNumberOfMeasureBlock;

  /**
   * will be used to read the dimension block from file
   */
  private int[][] allSelectedDimensionBlocksIndexes;

  /**
   * will be used to read the measure block from file
   */
  private int[][] allSelectedMeasureBlocksIndexes;

  /**
   * list of dimension present in the projection
   */
  private int[] projectionListDimensionIndexes;

  /**
   * list of dimension present in the projection
   */
  private int[] projectionListMeasureIndexes;

  /**
   * first block from which query execution will start
   */
  private DataRefNode firstDataBlock;

  /**
   * number of block to be scanned in the query
   */
  private long numberOfBlockToScan;

  /**
   * key size of the fixed length dimension column
   */
  private int fixedLengthKeySize;

  /**
   * dictionary column block indexes based on query
   */
  private int[] dictionaryColumnBlockIndex;
  /**
   * no dictionary column block indexes in based on the query order
   */
  private int[] noDictionaryBlockIndexes;

  /**
   * key generator used for generating the table block fixed length key
   */
  private KeyGenerator blockKeyGenerator;

  /**
   * each column value size
   */
  private int[] eachColumnValueSize;

  /**
   * filter tree to execute the filter
   */
  private FilterExecuter filterExecuterTree;

  /**
   * whether it needs only raw byte records with out aggregation.
   */
  private boolean isRawRecordDetailQuery;

  /**
   * start index of blocklets
   */
  private int startBlockletIndex;

  /**
   * number of blocklet to be scanned
   */
  private int numberOfBlockletToScan;

  /**
   * complexParentIndexToQueryMap
   */
  private Map<Integer, GenericQueryType> complexParentIndexToQueryMap;

  /**
   * complex dimension parent block indexes;
   */
  private int[] complexColumnParentBlockIndexes;

  /**
   * @return the tableBlock
   */
  public AbstractIndex getDataBlock() {
    return blockIndex;
  }

  /**
   * list of dimension present in the current block. This will be
   * different in case of restructured block
   */
  private QueryDimension[] queryDimensions;

  /**
   * list of dimension selected for in query
   */
  private QueryDimension[] actualQueryDimensions;

  /**
   * list of dimension present in the current block. This will be
   * different in case of restructured block
   */
  private QueryMeasure[] queryMeasures;

  /**
   * list of measure selected in query
   */
  private QueryMeasure[] actualQueryMeasures;

  /**
   * variable to maintain dimension existence and default value info
   */
  private DimensionInfo dimensionInfo;

  /**
   * whether it needs to read data in vector/columnar format.
   */
  private boolean vectorBatchCollector;

  /**
   * flag to specify that whether the current block is with latest schema or old schema
   */
  private boolean isRestructuredBlock;

  /**
   * delete delta file path
   */
  private String[] deleteDeltaFilePath;

  private Map<String, DeleteDeltaVo> deletedRecordsMap;

  /**
   * @param blockIndex the tableBlock to set
   */
  public void setDataBlock(AbstractIndex blockIndex) {
    this.blockIndex = blockIndex;
  }

  /**
   * @return the aggregatorInfos
   */
  public MeasureInfo getMeasureInfo() {
    return measureInfo;
  }

  /**
   * @param measureInfo the aggregatorInfos to set
   */
  public void setMeasureInfo(MeasureInfo measureInfo) {
    this.measureInfo = measureInfo;
  }

  /**
   * @return the startKey
   */
  public IndexKey getStartKey() {
    return startKey;
  }

  /**
   * @param startKey the startKey to set
   */
  public void setStartKey(IndexKey startKey) {
    this.startKey = startKey;
  }

  /**
   * @return the endKey
   */
  public IndexKey getEndKey() {
    return endKey;
  }

  /**
   * @param endKey the endKey to set
   */
  public void setEndKey(IndexKey endKey) {
    this.endKey = endKey;
  }

  /**
   * @return the totalNumberDimensionBlock
   */
  public int getTotalNumberDimensionBlock() {
    return totalNumberDimensionBlock;
  }

  /**
   * @param totalNumberDimensionBlock the totalNumberDimensionBlock to set
   */
  public void setTotalNumberDimensionBlock(int totalNumberDimensionBlock) {
    this.totalNumberDimensionBlock = totalNumberDimensionBlock;
  }

  /**
   * @return the totalNumberOfMeasureBlock
   */
  public int getTotalNumberOfMeasureBlock() {
    return totalNumberOfMeasureBlock;
  }

  /**
   * @param totalNumberOfMeasureBlock the totalNumberOfMeasureBlock to set
   */
  public void setTotalNumberOfMeasureBlock(int totalNumberOfMeasureBlock) {
    this.totalNumberOfMeasureBlock = totalNumberOfMeasureBlock;
  }

  /**
   * @return the allSelectedDimensionBlocksIndexes
   */
  public int[][] getAllSelectedDimensionBlocksIndexes() {
    return allSelectedDimensionBlocksIndexes;
  }

  /**
   * @param allSelectedDimensionBlocksIndexes the allSelectedDimensionBlocksIndexes to set
   */
  public void setAllSelectedDimensionBlocksIndexes(int[][] allSelectedDimensionBlocksIndexes) {
    this.allSelectedDimensionBlocksIndexes = allSelectedDimensionBlocksIndexes;
  }

  /**
   * @return the allSelectedMeasureBlocksIndexes
   */
  public int[][] getAllSelectedMeasureBlocksIndexes() {
    return allSelectedMeasureBlocksIndexes;
  }

  /**
   * @param allSelectedMeasureBlocksIndexes the allSelectedMeasureBlocksIndexes to set
   */
  public void setAllSelectedMeasureBlocksIndexes(int[][] allSelectedMeasureBlocksIndexes) {
    this.allSelectedMeasureBlocksIndexes = allSelectedMeasureBlocksIndexes;
  }

  /**
   * @return the firstDataBlock
   */
  public DataRefNode getFirstDataBlock() {
    return firstDataBlock;
  }

  /**
   * @param firstDataBlock the firstDataBlock to set
   */
  public void setFirstDataBlock(DataRefNode firstDataBlock) {
    this.firstDataBlock = firstDataBlock;
  }

  /**
   * @return the numberOfBlockToScan
   */
  public long getNumberOfBlockToScan() {
    return numberOfBlockToScan;
  }

  /**
   * @param numberOfBlockToScan the numberOfBlockToScan to set
   */
  public void setNumberOfBlockToScan(long numberOfBlockToScan) {
    this.numberOfBlockToScan = numberOfBlockToScan;
  }

  /**
   * @return the fixedLengthKeySize
   */
  public int getFixedLengthKeySize() {
    return fixedLengthKeySize;
  }

  /**
   * @param fixedLengthKeySize the fixedLengthKeySize to set
   */
  public void setFixedLengthKeySize(int fixedLengthKeySize) {
    this.fixedLengthKeySize = fixedLengthKeySize;
  }

  /**
   * @return the filterEvaluatorTree
   */
  public FilterExecuter getFilterExecuterTree() {
    return filterExecuterTree;
  }

  /**
   * @param filterExecuterTree the filterEvaluatorTree to set
   */
  public void setFilterExecuterTree(FilterExecuter filterExecuterTree) {
    this.filterExecuterTree = filterExecuterTree;
  }

  /**
   * @param tableBlockKeyGenerator the tableBlockKeyGenerator to set
   */
  public void setBlockKeyGenerator(KeyGenerator tableBlockKeyGenerator) {
    this.blockKeyGenerator = tableBlockKeyGenerator;
  }

  /**
   * @return the eachColumnValueSize
   */
  public int[] getEachColumnValueSize() {
    return eachColumnValueSize;
  }

  /**
   * @param eachColumnValueSize the eachColumnValueSize to set
   */
  public void setEachColumnValueSize(int[] eachColumnValueSize) {
    this.eachColumnValueSize = eachColumnValueSize;
  }

  /**
   * @return the dictionaryColumnBlockIndex
   */
  public int[] getDictionaryColumnBlockIndex() {
    return dictionaryColumnBlockIndex;
  }

  /**
   * @param dictionaryColumnBlockIndex the dictionaryColumnBlockIndex to set
   */
  public void setDictionaryColumnBlockIndex(int[] dictionaryColumnBlockIndex) {
    this.dictionaryColumnBlockIndex = dictionaryColumnBlockIndex;
  }

  /**
   * @return the noDictionaryBlockIndexes
   */
  public int[] getNoDictionaryBlockIndexes() {
    return noDictionaryBlockIndexes;
  }

  /**
   * @param noDictionaryBlockIndexes the noDictionaryBlockIndexes to set
   */
  public void setNoDictionaryBlockIndexes(int[] noDictionaryBlockIndexes) {
    this.noDictionaryBlockIndexes = noDictionaryBlockIndexes;
  }

  public boolean isRawRecordDetailQuery() {
    return isRawRecordDetailQuery;
  }

  public void setRawRecordDetailQuery(boolean rawRecordDetailQuery) {
    isRawRecordDetailQuery = rawRecordDetailQuery;
  }

  /**
   * @return the complexParentIndexToQueryMap
   */
  public Map<Integer, GenericQueryType> getComlexDimensionInfoMap() {
    return complexParentIndexToQueryMap;
  }

  /**
   * @param complexDimensionInfoMap the complexParentIndexToQueryMap to set
   */
  public void setComplexDimensionInfoMap(Map<Integer, GenericQueryType> complexDimensionInfoMap) {
    this.complexParentIndexToQueryMap = complexDimensionInfoMap;
  }

  /**
   * @return the complexColumnParentBlockIndexes
   */
  public int[] getComplexColumnParentBlockIndexes() {
    return complexColumnParentBlockIndexes;
  }

  /**
   * @param complexColumnParentBlockIndexes the complexColumnParentBlockIndexes to set
   */
  public void setComplexColumnParentBlockIndexes(int[] complexColumnParentBlockIndexes) {
    this.complexColumnParentBlockIndexes = complexColumnParentBlockIndexes;
  }

  public QueryDimension[] getQueryDimensions() {
    return queryDimensions;
  }

  public void setQueryDimensions(QueryDimension[] queryDimensions) {
    this.queryDimensions = queryDimensions;
  }

  public QueryMeasure[] getQueryMeasures() {
    return queryMeasures;
  }

  public void setQueryMeasures(QueryMeasure[] queryMeasures) {
    this.queryMeasures = queryMeasures;
  }

  /**
   * The method to set the number of blocklets to be scanned
   *
   * @param numberOfBlockletToScan
   */
  public void setNumberOfBlockletToScan(int numberOfBlockletToScan) {
    this.numberOfBlockletToScan = numberOfBlockletToScan;
  }

  /**
   * get the no of blocklet  to be scanned
   *
   * @return
   */
  public int getNumberOfBlockletToScan() {
    return numberOfBlockletToScan;
  }

  /**
   * returns the blocklet index to be scanned
   *
   * @return
   */
  public int getStartBlockletIndex() {
    return startBlockletIndex;
  }

  /**
   * set the blocklet index to be scanned
   *
   * @param startBlockletIndex
   */
  public void setStartBlockletIndex(int startBlockletIndex) {
    this.startBlockletIndex = startBlockletIndex;
  }

  public boolean isVectorBatchCollector() {
    return vectorBatchCollector;
  }

  public void setVectorBatchCollector(boolean vectorBatchCollector) {
    this.vectorBatchCollector = vectorBatchCollector;
  }

  public String getBlockId() {
    return blockId;
  }

  public void setBlockId(String blockId) {
    this.blockId = blockId;
  }

  public boolean isRestructuredBlock() {
    return isRestructuredBlock;
  }

  public void setRestructuredBlock(boolean restructuredBlock) {
    isRestructuredBlock = restructuredBlock;
  }

  public DimensionInfo getDimensionInfo() {
    return dimensionInfo;
  }

  public void setDimensionInfo(DimensionInfo dimensionInfo) {
    this.dimensionInfo = dimensionInfo;
  }

  public QueryDimension[] getActualQueryDimensions() {
    return actualQueryDimensions;
  }

  public void setActualQueryDimensions(QueryDimension[] actualQueryDimensions) {
    this.actualQueryDimensions = actualQueryDimensions;
  }

  public QueryMeasure[] getActualQueryMeasures() {
    return actualQueryMeasures;
  }

  public void setActualQueryMeasures(QueryMeasure[] actualQueryMeasures) {
    this.actualQueryMeasures = actualQueryMeasures;
  }

  public int[] getProjectionListDimensionIndexes() {
    return projectionListDimensionIndexes;
  }

  public void setProjectionListDimensionIndexes(int[] projectionListDimensionIndexes) {
    this.projectionListDimensionIndexes = projectionListDimensionIndexes;
  }

  public int[] getProjectionListMeasureIndexes() {
    return projectionListMeasureIndexes;
  }

  public void setProjectionListMeasureIndexes(int[] projectionListMeasureIndexes) {
    this.projectionListMeasureIndexes = projectionListMeasureIndexes;
  }

  /**
   * @return delete delta files
   */
  public String[] getDeleteDeltaFilePath() {
    return deleteDeltaFilePath;
  }

  /**
   * set the delete delta files
   * @param deleteDeltaFilePath
   */
  public void setDeleteDeltaFilePath(String[] deleteDeltaFilePath) {
    this.deleteDeltaFilePath = deleteDeltaFilePath;
  }

  /**
   * @return deleted record map
   */
  public Map<String, DeleteDeltaVo> getDeletedRecordsMap() {
    return deletedRecordsMap;
  }

  /**
   * @param deletedRecordsMap
   */
  public void setDeletedRecordsMap(Map<String, DeleteDeltaVo> deletedRecordsMap) {
    this.deletedRecordsMap = deletedRecordsMap;
  }
}
