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
package org.apache.carbondata.core.indexstore.blockletindex;

import java.io.IOException;
import java.util.List;

import org.apache.carbondata.core.datastore.DataRefNode;
import org.apache.carbondata.core.datastore.FileHolder;
import org.apache.carbondata.core.datastore.block.TableBlockInfo;
import org.apache.carbondata.core.datastore.chunk.impl.DimensionRawColumnChunk;
import org.apache.carbondata.core.datastore.chunk.impl.MeasureRawColumnChunk;
import org.apache.carbondata.core.datastore.chunk.reader.CarbonDataReaderFactory;
import org.apache.carbondata.core.datastore.chunk.reader.DimensionColumnChunkReader;
import org.apache.carbondata.core.datastore.chunk.reader.MeasureColumnChunkReader;
import org.apache.carbondata.core.metadata.ColumnarFormatVersion;

/**
 * wrapper for blocklet data map data
 */
public class BlockletDataRefNodeWrapper implements DataRefNode {

  private List<TableBlockInfo> blockInfos;

  private int index;

  private int[] dimensionLens;

  public BlockletDataRefNodeWrapper(List<TableBlockInfo> blockInfos, int index,
      int[] dimensionLens) {
    this.blockInfos = blockInfos;
    this.index = index;
    this.dimensionLens = dimensionLens;
  }

  @Override public DataRefNode getNextDataRefNode() {
    if (index + 1 < blockInfos.size()) {
      return new BlockletDataRefNodeWrapper(blockInfos, index + 1, dimensionLens);
    }
    return null;
  }

  @Override public int nodeSize() {
    return blockInfos.get(index).getDetailInfo().getRowCount();
  }

  @Override public long nodeNumber() {
    return index;
  }

  @Override public byte[][] getColumnsMaxValue() {
    return null;
  }

  @Override public byte[][] getColumnsMinValue() {
    return null;
  }

  @Override
  public DimensionRawColumnChunk[] getDimensionChunks(FileHolder fileReader, int[][] blockIndexes)
      throws IOException {
    DimensionColumnChunkReader dimensionChunksReader = getDimensionColumnChunkReader();
    return dimensionChunksReader.readRawDimensionChunks(fileReader, blockIndexes);
  }

  @Override
  public DimensionRawColumnChunk getDimensionChunk(FileHolder fileReader, int blockIndexes)
      throws IOException {
    DimensionColumnChunkReader dimensionChunksReader = getDimensionColumnChunkReader();
    return dimensionChunksReader.readRawDimensionChunk(fileReader, blockIndexes);
  }

  @Override
  public MeasureRawColumnChunk[] getMeasureChunks(FileHolder fileReader, int[][] blockIndexes)
      throws IOException {
    MeasureColumnChunkReader measureColumnChunkReader = getMeasureColumnChunkReader();
    return measureColumnChunkReader.readRawMeasureChunks(fileReader, blockIndexes);
  }

  @Override public MeasureRawColumnChunk getMeasureChunk(FileHolder fileReader, int blockIndex)
      throws IOException {
    MeasureColumnChunkReader measureColumnChunkReader = getMeasureColumnChunkReader();
    return measureColumnChunkReader.readRawMeasureChunk(fileReader, blockIndex);
  }

  private DimensionColumnChunkReader getDimensionColumnChunkReader() throws IOException {
    ColumnarFormatVersion version =
        ColumnarFormatVersion.valueOf(blockInfos.get(index).getDetailInfo().getVersionNumber());
    DimensionColumnChunkReader dimensionColumnChunkReader = CarbonDataReaderFactory.getInstance()
        .getDimensionColumnChunkReader(version,
            blockInfos.get(index).getDetailInfo().getBlockletInfo(), dimensionLens,
            blockInfos.get(index).getFilePath());
    return dimensionColumnChunkReader;
  }

  private MeasureColumnChunkReader getMeasureColumnChunkReader() throws IOException {
    ColumnarFormatVersion version =
        ColumnarFormatVersion.valueOf(blockInfos.get(index).getDetailInfo().getVersionNumber());
    return CarbonDataReaderFactory.getInstance().getMeasureColumnChunkReader(version,
        blockInfos.get(index).getDetailInfo().getBlockletInfo(),
        blockInfos.get(index).getFilePath());
  }

  @Override public int numberOfPages() {
    return blockInfos.get(index).getDetailInfo().getPagesCount();
  }

  public int numberOfNodes() {
    return blockInfos.size();
  }
}
