package model.components.wires;

import java.util.HashSet;

import config.Config;
import model.Vector3;
import model.components.Block;
import model.components.datatypes.BitBlock;
import model.grid.Grid;

public class BitWire extends Wire implements BitBlock{

	private boolean data; 
	
	public BitWire(Grid grid, Vector3 position, byte insulated) {
		super(grid, position, insulated);
		this.dataType = Config.TYPE_BIT;
	}

	@Override
	public void sendSignal(HashSet<Block> blocks) {
		// if the block hasn't been updated
		if( this.hasNotBeenUpdated(blocks) ){
			// we reset the data to false
			this.data = false;
			// for each block around the wire
			for( byte i=0; i<Config.NUM_OF_DIRECTIONS; ++i ){
				Block block = this.getBlockAt(i);
				if( block != null ){
					// we update the block
					block.sendSignal(blocks);
					// if the block is of the same data type
					if( block instanceof BitBlock ){
						((BitBlock) block).setData(this.data);
					}
				}
			}
		}
	}

	@Override
	public void receiveSignal(HashSet<Block> blocks) {
		// if the block hasn't been updated
		if( this.hasNotBeenUpdated(blocks) ){
			// we reset the data to false
			this.data = false;
			// for each block around the wire
			for( byte i=0; i<Config.NUM_OF_DIRECTIONS; ++i ){
				Block block = this.getBlockAt(i);
				if( block != null ){
					// we update the block
					block.receiveSignal(blocks);
					// if the block is of the same data type
					if( block instanceof BitBlock ){
						this.data |= ((BitBlock) block).getData();
					}
				}
			}
		}
	}

	@Override
	public void setData(boolean data) {
		this.data |= data;
	}

	@Override
	public boolean getData() {
		return this.data;
	}

}
