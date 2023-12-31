// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
      // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
        if(freeBlk.getFirst()==null)
            return;
        Dictionary fragment;
        if(type==2)
            fragment = new BSTree();
        else
            fragment = new AVLTree();
        Dictionary i;
        for(i=freeBlk.getFirst();i!=null;i=i.getNext())
        {
            fragment.Insert(i.address,i.size,i.address);
        }
        for(i=fragment.getFirst();i!=null;i=i.getNext())
        {
            Dictionary temp = i.getNext();
            Dictionary temp1,temp2;
            while(temp!=null&&i.address+i.size==temp.address)
            {
                if(type==2)
                {
                    temp1 = new BSTree(i.address,i.size,i.size);
                    temp2 = new BSTree(temp.address,temp.size,temp.size);
                }
                else
                {
                    temp1 = new AVLTree(i.address,i.size,i.size);
                    temp2 = new AVLTree(temp.address,temp.size,temp.size);
                }
                freeBlk.Delete(temp1);
                freeBlk.Delete(temp2);
                fragment.Delete(i);
                fragment.Delete(temp);
                freeBlk.Insert(i.address,i.size+temp.size,i.size+temp.size);
                i=fragment.Insert(i.address,i.size+temp.size,i.address);
                temp = i.getNext();
            }
        }
        fragment = null;
        return ;
    }
}