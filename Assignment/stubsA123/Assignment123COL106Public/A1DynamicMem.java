// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the dictionary class implementation) are to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem). Similar arguments will follow with regards to the ROOT in the case of trees (specifying this in case it was not so trivial to anyone of you earlier)
    public int Allocate(int blockSize) {
        if(blockSize<=0)
            return -1;
        Dictionary memory = freeBlk.Find(blockSize,false);
        //if(!freeBlk.sanity())
            //return -1;
            
        // if memory is found
        if (memory!=null)
        {
            //checking if split is needed
            if(memory.key>blockSize)
            {
                //first deleting the found memory
                //then inserting the requisite memory in freeBlk and allocBlk
                memory.Delete(memory);
                freeBlk.Insert(memory.address+blockSize,memory.key-blockSize,memory.key-blockSize);                
                allocBlk.Insert(memory.address,blockSize,memory.address);
                return memory.address;
            }
            //since no splitted is needed , first deleting the memory from freeBlk and then inserting it into allocBlk
            allocBlk.Insert(memory.address,blockSize,memory.address);
            memory.Delete(memory);
            //if(!freeBlk.sanity()||!allocBlk.sanity())
                //return -1;
            return memory.address;
        }
        return -1;
    } 
    
    public int Free(int startAddr) {
        Dictionary memory = allocBlk.Find(startAddr,true);
        //if memory is found
        if(memory!=null)
        {
            //inserting a found memory at front freeBlk and deleting it from allocated block
            freeBlk.Insert(startAddr,memory.size,memory.size);
            memory.Delete(memory);
            //if(!freeBlk.sanity()||!allocBlk.sanity())
                //return -1;
            return 0;
        }
        return -1;
    }
}