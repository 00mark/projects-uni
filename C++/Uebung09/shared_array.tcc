namespace asteroids
{

template<class T> shared_array<T>::shared_array(T* ptr) :
    std::shared_ptr<T>(ptr, [](auto p) { delete[] p; })
{
}

template<class T> T shared_array<T>::operator[](const int& index) const
{
    return this->get()[index];
}

template<class T> T& shared_array<T>::operator[](const int& index)
{
    return this->get()[index];
}

}
